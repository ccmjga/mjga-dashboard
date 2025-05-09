package com.zl.mjga.repository;

import static org.jooq.generated.mjga.Tables.*;
import static org.jooq.generated.mjga.tables.User.USER;
import static org.jooq.impl.DSL.*;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.urp.PermissionRespDto;
import com.zl.mjga.dto.urp.RoleDto;
import com.zl.mjga.dto.urp.UserQueryDto;
import com.zl.mjga.dto.urp.UserRolePermissionDto;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.generated.mjga.tables.daos.*;
import org.jooq.generated.mjga.tables.pojos.User;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository extends UserDao {

  @Autowired
  public UserRepository(Configuration configuration) {
    super(configuration);
  }

  @Transactional
  public void mergeWithoutNullFieldBy(User user) {
    ctx()
        .mergeInto(USER)
        .using(
            select(
                    value(user.getId()).as("id"),
                    value(user.getUsername()).as("username"),
                    value(user.getPassword()).as("password"),
                    value(user.getEnable()).as("enable"))
                .asTable("newUser"))
        .on(USER.ID.eq(DSL.field(DSL.name("newUser", "id"), Long.class)))
        .whenMatchedThenUpdate()
        .set(USER.USERNAME, DSL.field(DSL.name("newUser", "username"), String.class))
        .set(
            USER.PASSWORD,
            StringUtils.isNotEmpty(user.getPassword())
                ? DSL.field(DSL.name("newUser", "password"), String.class)
                : USER.PASSWORD)
        .set(USER.ENABLE, DSL.field(DSL.name("newUser", "enable"), Boolean.class))
        .whenNotMatchedThenInsert(USER.USERNAME, USER.PASSWORD, USER.ENABLE)
        .values(
            DSL.field(DSL.name("newUser", "username"), String.class),
            DSL.field(DSL.name("newUser", "password"), String.class),
            DSL.field(DSL.name("newUser", "enable"), Boolean.class))
        .execute();
  }

  public Result<Record> pageFetchBy(PageRequestDto pageRequestDto, UserQueryDto userQueryDto) {
    return ctx()
        .select(asterisk(), DSL.count().over().as("total_user"))
        .from(USER)
        .where(
            userQueryDto.getUsername() != null
                ? USER.USERNAME.like("%" + userQueryDto.getUsername() + "%")
                : noCondition())
        .orderBy(pageRequestDto.getSortFields())
        .limit(pageRequestDto.getSize())
        .offset(pageRequestDto.getOffset())
        .fetch();
  }

  public UserRolePermissionDto fetchUniqueUserDtoWithNestedRolePermissionBy(Long userId) {
    return ctx()
        .select(
            USER.asterisk(),
            multiset(
                    select(
                            USER.role().asterisk(),
                            multiset(
                                    select(USER.role().permission().asterisk())
                                        .from(USER)
                                        .leftJoin(USER.role())
                                        .leftJoin(USER.role().permission()))
                                .convertFrom(
                                    r -> r.map((record) -> record.into(PermissionRespDto.class)))
                                .as("permissions"))
                        .from(USER)
                        .leftJoin(USER.role()))
                .convertFrom(r -> r.map((record) -> record.into(RoleDto.class)))
                .as("roles"))
        .from(USER)
        .where(USER.ID.eq(userId))
        .fetchOneInto(UserRolePermissionDto.class);
  }

  //  public UserRolePermissionDto fetchUniqueUserDtoWithNestedRolePermissionBy(Long roleId) {
  //    return ctx()
  //        .select(
  //            USER.asterisk(),
  //            multiset(
  //                    select(
  //                            ROLE.asterisk(),
  //                            multiset(
  //                                    select(PERMISSION.asterisk())
  //                                        .from(ROLE_PERMISSION_MAP)
  //                                        .leftJoin(PERMISSION)
  //                                        .on(ROLE_PERMISSION_MAP.PERMISSION_ID.eq(PERMISSION.ID))
  //                                        .where(ROLE_PERMISSION_MAP.ROLE_ID.eq(ROLE.ID)))
  //                                .convertFrom(
  //                                    r -> r.map((record) ->
  // record.into(PermissionRespDto.class)))
  //                                .as("permissions"))
  //                        .from(USER_ROLE_MAP)
  //                        .leftJoin(ROLE)
  //                        .on(USER_ROLE_MAP.ROLE_ID.eq(ROLE.ID))
  //                        .where(USER.ID.eq(USER_ROLE_MAP.USER_ID)))
  //                .convertFrom(r -> r.map((record) -> record.into(RoleDto.class)))
  //                .as("roles"))
  //        .from(USER)
  //        .where(USER.ID.eq(roleId))
  //        .fetchOneInto(UserRolePermissionDto.class);
  //  }

  public Result<Record> fetchUniqueUserWithRolePermissionBy(Long userId) {
    return ctx()
        .select(USER.asterisk(), USER.role().asterisk(), USER.role().permission().asterisk())
        .from(USER)
        .leftJoin(USER.role())
        .leftJoin(USER.role().permission())
        .where(USER.ID.eq(userId))
        .fetch();
  }

  //    public Result<Record> fetchUniqueUserWithRolePermissionBy(Long roleId) {
  //      return ctx()
  //          .select()
  //          .from(USER)
  //          .leftJoin(USER_ROLE_MAP)
  //          .on(USER.ID.eq(USER_ROLE_MAP.USER_ID))
  //          .leftJoin(ROLE)
  //          .on(USER_ROLE_MAP.ROLE_ID.eq(ROLE.ID))
  //          .leftJoin(ROLE_PERMISSION_MAP)
  //          .on(ROLE.ID.eq(ROLE_PERMISSION_MAP.ROLE_ID))
  //          .leftJoin(PERMISSION)
  //          .on(ROLE_PERMISSION_MAP.PERMISSION_ID.eq(PERMISSION.ID))
  //          .where(USER.ID.eq(roleId))
  //          .fetch();
  //    }

  @Transactional
  public void deleteByUsername(String username) {
    ctx().delete(USER).where(USER.USERNAME.eq(username)).execute();
  }
}
