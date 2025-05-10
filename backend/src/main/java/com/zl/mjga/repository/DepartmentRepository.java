package com.zl.mjga.repository;

import static org.jooq.generated.mjga.Tables.*;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.noField;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.department.DepartmentQueryDto;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.generated.mjga.tables.Department;
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
    Department parent = DEPARTMENT.as("parent");
    return ctx()
        .select(
            DEPARTMENT.asterisk(),
            parent.NAME.as("parent_name"),
            departmentQueryDto.getUserId() != null
                ? DSL.when(
                        DEPARTMENT.ID.in(selectUsersDepartment(departmentQueryDto.getUserId())),
                        true)
                    .otherwise(false)
                    .as("is_bound")
                : noField(),
            DSL.count().over().as("total_department").convertFrom(Long::valueOf))
        .from(DEPARTMENT)
        .leftJoin(parent)
        .on(parent.ID.eq(DEPARTMENT.PARENT_ID))
        .where(
            switch (departmentQueryDto.getBindState()) {
              case BIND -> DEPARTMENT.ID.in(selectUsersDepartment(departmentQueryDto.getUserId()));
              case UNBIND ->
                  DEPARTMENT.ID.notIn(selectUsersDepartment(departmentQueryDto.getUserId()));
              case ALL -> noCondition();
            })
        .and(
            StringUtils.isNotEmpty(departmentQueryDto.getName())
                ? DEPARTMENT.NAME.like("%" + departmentQueryDto.getName() + "%")
                : noCondition())
        .orderBy(pageRequestDto.getSortFields())
        .limit(pageRequestDto.getSize())
        .offset(pageRequestDto.getOffset())
        .fetch();
  }

  private SelectConditionStep<Record1<Long>> selectUsersDepartment(Long userId) {
    return DSL.select(USER.department().ID)
        .from(USER)
        .leftJoin(USER.department())
        .where(USER.ID.eq(userId));
  }
}
