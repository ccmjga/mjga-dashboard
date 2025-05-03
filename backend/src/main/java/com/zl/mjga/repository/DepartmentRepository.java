package com.zl.mjga.repository;

import static org.jooq.generated.mjga.Tables.DEPARTMENT;
import static org.jooq.generated.mjga.Tables.USER_DEPARTMENT_MAP;
import static org.jooq.generated.mjga.tables.Permission.PERMISSION;
import static org.jooq.generated.mjga.tables.Role.ROLE;
import static org.jooq.impl.DSL.noCondition;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.department.DepartmentQueryDto;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.generated.mjga.tables.daos.DepartmentDao;
import org.jooq.generated.mjga.tables.pojos.Department;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepository extends DepartmentDao {

  @Autowired
  public DepartmentRepository(Configuration configuration) {
    super(configuration);
  }

  public List<Department> fetchBy(DepartmentQueryDto departmentQueryDto) {
    return ctx()
        .select(DEPARTMENT.asterisk())
        .from(DEPARTMENT)
        .where(
            switch (departmentQueryDto.getBindState()) {
              case BIND -> PERMISSION.ID.in(selectUsersDepartment(departmentQueryDto));
              case UNBIND -> ROLE.ID.notIn(selectUsersDepartment(departmentQueryDto));
              case ALL -> noCondition();
            })
        .and(
            StringUtils.isNotEmpty(departmentQueryDto.getName())
                ? DEPARTMENT.NAME.like("%" + departmentQueryDto.getName() + "%")
                : noCondition())
        .and(
            departmentQueryDto.getEnable() != null
                ? DEPARTMENT.ENABLE.eq(departmentQueryDto.getEnable())
                : noCondition())
        .fetchInto(Department.class);
  }

  public Result<Record> pageFetchBy(
      PageRequestDto pageRequestDto, DepartmentQueryDto departmentQueryDto) {
    return ctx()
        .select(
            DEPARTMENT.asterisk(),
            DSL.count().over().as("total_department").convertFrom(Long::valueOf))
        .from(DEPARTMENT)
        .where(
            switch (departmentQueryDto.getBindState()) {
              case BIND -> PERMISSION.ID.in(selectUsersDepartment(departmentQueryDto));
              case UNBIND -> ROLE.ID.notIn(selectUsersDepartment(departmentQueryDto));
              case ALL -> noCondition();
            })
        .and(
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

  private SelectConditionStep<Record1<Long>> selectUsersDepartment(
      DepartmentQueryDto departmentQueryDto) {
    return ctx()
        .select(USER_DEPARTMENT_MAP.DEPARTMENT_ID)
        .from(USER_DEPARTMENT_MAP)
        .where(USER_DEPARTMENT_MAP.USER_ID.eq(departmentQueryDto.getUserId()));
  }
}
