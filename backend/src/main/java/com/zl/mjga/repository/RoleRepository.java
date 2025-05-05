package com.zl.mjga.repository;

import static org.jooq.generated.mjga.Tables.USER;
import static org.jooq.generated.mjga.tables.Role.ROLE;
import static org.jooq.impl.DSL.*;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.urp.RoleQueryDto;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.generated.mjga.tables.daos.RoleDao;
import org.jooq.generated.mjga.tables.pojos.Permission;
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
                ? when(ROLE.ID.in(selectUsersRoleIds(roleQueryDto.getUserId())), true)
                    .otherwise(false)
                    .as("is_bound")
                : noField(),
            multiset(select(ROLE.permission().asterisk()).from(ROLE.permission()))
                .convertFrom(r -> r.into(Permission.class))
                .as("permissions"),
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
    return DSL.select(USER.role().ID)
        .from(USER)
        .leftJoin(USER.role())
        .where(USER.role().ID.eq(userId));
  }

  public Result<Record> fetchUniqueRoleWithPermission(Long roleId) {
    return ctx()
        .select(ROLE.asterisk(), ROLE.permission().asterisk())
        .from(ROLE, ROLE.permission())
        .where(ROLE.ID.eq(roleId))
        .orderBy(ROLE.ID)
        .fetch();
  }
}
