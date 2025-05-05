package com.zl.mjga.repository;

import static org.jooq.generated.mjga.Tables.*;
import static org.jooq.generated.mjga.tables.Permission.PERMISSION;
import static org.jooq.generated.mjga.tables.Role.ROLE;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.noField;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.department.DepartmentQueryDto;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.Record;
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
            departmentQueryDto.getUserId() != null
                ? DSL.when(
                        DEPARTMENT.ID.in(selectUsersDepartment(departmentQueryDto.getUserId())),
                        true)
                    .otherwise(false)
                    .as("is_bound")
                : noField(),
            DSL.when(
                    DEPARTMENT.PARENT_ID.isNotNull(),
                    DSL.select(DEPARTMENT.NAME)
                        .from(DEPARTMENT)
                        .where(DEPARTMENT.ID.eq(DEPARTMENT.PARENT_ID))
                        .asField("parent_name"))
                .otherwise(noField()),
            DSL.count().over().as("total_department").convertFrom(Long::valueOf))
        .from(DEPARTMENT)
        .where(
            switch (departmentQueryDto.getBindState()) {
              case BIND -> PERMISSION.ID.in(selectUsersDepartment(departmentQueryDto.getUserId()));
              case UNBIND -> ROLE.ID.notIn(selectUsersDepartment(departmentQueryDto.getUserId()));
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
