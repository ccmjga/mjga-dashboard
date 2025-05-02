package com.zl.mjga.repository;

import static org.jooq.generated.mjga.Tables.DEPARTMENT;
import static org.jooq.impl.DSL.noCondition;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.department.DepartmentQueryDto;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.generated.mjga.tables.daos.DepartmentDao;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepository extends DepartmentDao {

  @Autowired
  public DepartmentRepository(Configuration configuration) {
    super(configuration);
  }

  public Result<Record> pageFetchBy(
      PageRequestDto pageRequestDto, DepartmentQueryDto departmentQueryDto) {
    return ctx()
        .select(
            DEPARTMENT.asterisk(),
            DSL.count().over().as("total_department").convertFrom(Long::valueOf))
        .from(DEPARTMENT)
        .where(
            StringUtils.isNotEmpty(departmentQueryDto.getName())
                ? DEPARTMENT.NAME.like("%" + departmentQueryDto.getName() + "%")
                : noCondition())
        .and(
            departmentQueryDto.getEnable() != null
                ? DEPARTMENT.ENABLE.eq(departmentQueryDto.getEnable())
                : noCondition())
        .orderBy(pageRequestDto.getSortFields())
        .limit(pageRequestDto.getSize())
        .offset(pageRequestDto.getOffset())
        .fetch();
  }
}
