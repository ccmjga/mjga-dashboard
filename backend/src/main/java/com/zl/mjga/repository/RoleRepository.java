package com.zl.mjga.repository;

import static org.jooq.generated.mjga.Tables.DEPARTMENT;
import static org.jooq.generated.mjga.tables.Permission.PERMISSION;
import static org.jooq.generated.mjga.tables.Role.ROLE;
import static org.jooq.generated.mjga.tables.RolePermissionMap.ROLE_PERMISSION_MAP;
import static org.jooq.generated.mjga.tables.UserRoleMap.USER_ROLE_MAP;
import static org.jooq.impl.DSL.*;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.urp.RoleQueryDto;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.generated.mjga.tables.daos.RoleDao;
import org.jooq.generated.mjga.tables.pojos.Role;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository extends RoleDao {

  @Autowired
  public RoleRepository(Configuration configuration) {
    super(configuration);
  }

  public List<Role> selectByRoleCodeIn(List<String> roleCodeList) {
    return ctx().selectFrom(ROLE).where(ROLE.CODE.in(roleCodeList)).fetchInto(Role.class);
  }

  public List<Role> selectByRoleIdIn(List<Long> roleIdList) {
    return ctx().selectFrom(ROLE).where(ROLE.ID.in(roleIdList)).fetchInto(Role.class);
  }

  public Result<Record> pageFetchBy(PageRequestDto pageRequestDto, RoleQueryDto roleQueryDto) {
    return ctx()
        .select(
            asterisk(),
            roleQueryDto.getUserId() != null
                ? DSL.when(DEPARTMENT.ID.in(selectUsersRoleIds(roleQueryDto.getUserId())), true)
                    .otherwise(false)
                    .as("is_bound")
                : noField(),
            DSL.count(ROLE.ID).over().as("total_role"))
        .from(ROLE)
        .where(
            switch (roleQueryDto.getBindState()) {
              case BIND -> ROLE.ID.in(selectUsersRoleIds(roleQueryDto.getUserId()));
              case UNBIND -> ROLE.ID.notIn(selectUsersRoleIds(roleQueryDto.getUserId()));
              case ALL -> noCondition();
            })
        .and(
            roleQueryDto.getRoleId() == null ? noCondition() : ROLE.ID.eq(roleQueryDto.getRoleId()))
        .and(
            StringUtils.isEmpty(roleQueryDto.getRoleName())
                ? noCondition()
                : ROLE.NAME.like("%" + roleQueryDto.getRoleName() + "%"))
        .and(
            StringUtils.isEmpty(roleQueryDto.getRoleCode())
                ? noCondition()
                : ROLE.CODE.eq(roleQueryDto.getRoleCode()))
        .orderBy(pageRequestDto.getSortFields())
        .limit(pageRequestDto.getSize())
        .offset(pageRequestDto.getOffset())
        .fetch();
  }

  private SelectConditionStep<Record1<Long>> selectUsersRoleIds(Long userId) {
    return ctx()
        .select(USER_ROLE_MAP.ROLE_ID)
        .from(USER_ROLE_MAP)
        .where(USER_ROLE_MAP.USER_ID.eq(userId));
  }

  public Result<Record> fetchUniqueRoleWithPermission(Long roleId) {
    return ctx()
        .select(asterisk())
        .from(ROLE)
        .leftJoin(ROLE_PERMISSION_MAP)
        .on(ROLE.ID.eq(ROLE_PERMISSION_MAP.ROLE_ID))
        .leftJoin(PERMISSION)
        .on(ROLE_PERMISSION_MAP.PERMISSION_ID.eq(PERMISSION.ID))
        .where(ROLE.ID.eq(roleId))
        .orderBy(ROLE.ID)
        .fetch();
  }
}
