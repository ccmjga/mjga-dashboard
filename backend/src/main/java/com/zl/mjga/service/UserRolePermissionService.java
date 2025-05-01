package com.zl.mjga.service;

import static org.jooq.generated.mjga.tables.Permission.PERMISSION;
import static org.jooq.generated.mjga.tables.Role.ROLE;
import static org.jooq.generated.mjga.tables.User.USER;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.PageResponseDto;
import com.zl.mjga.dto.urp.*;
import com.zl.mjga.exception.BusinessException;
import com.zl.mjga.model.urp.ERole;
import com.zl.mjga.repository.*;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.generated.mjga.tables.pojos.*;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRolePermissionService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserRoleMapRepository userRoleMapRepository;
  private final PermissionRepository permissionRepository;
  private final RolePermissionMapRepository rolePermissionMapRepository;

  public void upsertUser(UserUpsertDto userUpsertDto) {
    User user = new User();
    BeanUtils.copyProperties(userUpsertDto, user);
    if (user.getId() == null) {
      if (isUsernameDuplicate(user.getUsername())) {
        throw new BusinessException(
            String.format("username %s already exist", userUpsertDto.getUsername()));
      }
      userRepository.insert(user);
    } else {
      userRepository.update(user);
    }
  }

  public void upsertRole(RoleUpsertDto roleUpsertDto) {
    Role role = new Role();
    BeanUtils.copyProperties(roleUpsertDto, role);
    if (role.getId() == null) {
      if (isRoleDuplicate(roleUpsertDto.getCode(), roleUpsertDto.getName())) {
        throw new BusinessException(
            String.format("role %s already exist", roleUpsertDto.getName()));
      }
      roleRepository.insert(role);
    } else {
      roleRepository.update(role);
    }
  }

  public void upsertPermission(PermissionUpsertDto permissionUpsertDto) {
    Permission permission = new Permission();
    BeanUtils.copyProperties(permissionUpsertDto, permission);
    if (permission.getId() == null) {
      if (isPermissionDuplicate(permissionUpsertDto.getCode(), permissionUpsertDto.getName())) {
        throw new BusinessException(
            String.format("permission %s already exist", permissionUpsertDto.getName()));
      }
      permissionRepository.insert(permission);
    } else {
      permissionRepository.update(permission);
    }
  }

  public PageResponseDto<List<UserRolePermissionDto>> pageQueryUser(
      PageRequestDto pageRequestDto, UserQueryDto userQueryDto) {
    Result<Record> userRecords = userRepository.pageFetchBy(pageRequestDto, userQueryDto);
    if (userRecords.isEmpty()) {
      return PageResponseDto.empty();
    }
    List<UserRolePermissionDto> userRolePermissionDtoList =
        userRecords.stream()
            .map((record) -> queryUniqueUserWithRolePermission(record.getValue(USER.ID)))
            .toList();
    return new PageResponseDto<>(
        userRecords.get(0).getValue("total_user", Integer.class), userRolePermissionDtoList);
  }

  public @Nullable UserRolePermissionDto queryUniqueUserWithRolePermission(Long userId) {
    return userRepository.fetchUniqueUserDtoWithNestedRolePermissionBy(userId);
  }

  public PageResponseDto<List<RoleDto>> pageQueryRole(
      PageRequestDto pageRequestDto, RoleQueryDto roleQueryDto) {
    Result<Record> roleRecords = roleRepository.pageFetchBy(pageRequestDto, roleQueryDto);
    if (roleRecords.isEmpty()) {
      return PageResponseDto.empty();
    }
    List<RoleDto> roleDtoList =
        roleRecords.stream()
            .map(record -> queryUniqueRoleWithPermission(record.getValue(ROLE.ID)))
            .toList();
    return new PageResponseDto<>(
        roleRecords.get(0).getValue("total_role", Integer.class), roleDtoList);
  }

  public @Nullable RoleDto queryUniqueRoleWithPermission(Long roleId) {
    Result<Record> roleWithPermissionRecords = roleRepository.fetchUniqueRoleWithPermission(roleId);
    if (roleWithPermissionRecords.isEmpty()) {
      return null;
    }
    RoleDto roleDto = createRbacDtoRolePart(roleWithPermissionRecords);
    setCurrentRolePermission(roleDto, roleWithPermissionRecords);
    return roleDto;
  }

  public PageResponseDto<List<PermissionDto>> pageQueryPermission(
      PageRequestDto pageRequestDto, PermissionQueryDto permissionQueryDto) {
    if (permissionQueryDto.getRoleId() != null) {
      List<Long> permissionIdList =
          rolePermissionMapRepository.fetchByRoleId(permissionQueryDto.getRoleId()).stream()
              .map(RolePermissionMap::getPermissionId)
              .toList();
      if (permissionIdList.isEmpty()) {
        return PageResponseDto.empty();
      } else {
        permissionQueryDto.setPermissionIdList(permissionIdList);
      }
    }
    Result<Record> permissionRecords =
        permissionRepository.pageFetchBy(pageRequestDto, permissionQueryDto);
    if (permissionRecords.isEmpty()) {
      return PageResponseDto.empty();
    }
    List<PermissionDto> permissionDtoList =
        permissionRecords.into(Permission.class).stream()
            .map(pojo -> new PermissionDto(pojo.getId(), pojo.getName(), pojo.getCode()))
            .toList();
    return new PageResponseDto<>(
        permissionRecords.get(0).getValue("total_permission", Integer.class), permissionDtoList);
  }

  public void bindPermissionToRole(Long roleId, List<Long> permissionIdList) {
    List<Permission> permissions = permissionRepository.selectByPermissionIdIn(permissionIdList);
    if (CollectionUtils.isEmpty(permissions)) {
      throw new BusinessException("bind permission not exist");
    }
    List<RolePermissionMap> permissionMapList =
        permissions.stream()
            .map(
                (permission -> {
                  RolePermissionMap rolePermissionMap = new RolePermissionMap();
                  rolePermissionMap.setRoleId(roleId);
                  rolePermissionMap.setPermissionId(permission.getId());
                  return rolePermissionMap;
                }))
            .collect(Collectors.toList());
    rolePermissionMapRepository.insert(permissionMapList);
  }

  public void unBindPermissionToRole(Long roleId, List<Long> permissionIdList) {
    if (CollectionUtils.isEmpty(permissionIdList)) {
      return;
    }
    List<Permission> permissions = permissionRepository.selectByPermissionIdIn(permissionIdList);
    if (CollectionUtils.isEmpty(permissions)) {
      throw new BusinessException("unbind role not exist");
    }
    rolePermissionMapRepository.deleteBy(roleId, permissionIdList);
  }

  public @NonNull List<Long> removeDuplicateRoleId(Long userId, List<Long> roleIdList) {
    UserRolePermissionDto userRolePermissionDto =
        userRepository.fetchUniqueUserDtoWithNestedRolePermissionBy(userId);
    List<Long> userRoleIdList =
        userRolePermissionDto.getRoles().stream().map(RoleDto::getId).toList();
    return roleIdList.stream().filter(roleId -> !userRoleIdList.contains(roleId)).toList();
  }

  public @NonNull List<Long> removeDuplicatePermissionId(Long roleId, List<Long> permissionIdList) {
    List<Long> rolePermissionIdList =
        rolePermissionMapRepository.fetchByRoleId(roleId).stream()
            .map(RolePermissionMap::getPermissionId)
            .toList();
    return permissionIdList.stream()
        .filter(permissionId -> !rolePermissionIdList.contains(permissionId))
        .toList();
  }

  public void unBindRoleToUser(Long userId, List<Long> roleIdList) {
    if (CollectionUtils.isEmpty(roleIdList)) {
      return;
    }
    List<Role> roles = roleRepository.selectByRoleIdIn(roleIdList);
    if (CollectionUtils.isEmpty(roles)) {
      throw new BusinessException("unbind role not exist");
    }
    userRoleMapRepository.deleteBy(userId, roleIdList);
  }

  public void bindRoleToUser(Long userId, List<Long> roleIdList) {
    List<Role> roles = roleRepository.selectByRoleIdIn(roleIdList);
    if (CollectionUtils.isEmpty(roles)) {
      throw new BusinessException("bind role not exist");
    }
    List<UserRoleMap> userRoleMapList =
        roles.stream()
            .map(
                (role -> {
                  UserRoleMap userRoleMap = new UserRoleMap();
                  userRoleMap.setUserId(userId);
                  userRoleMap.setRoleId(role.getId());
                  return userRoleMap;
                }))
            .collect(Collectors.toList());
    userRoleMapRepository.insert(userRoleMapList);
  }

  @Transactional(rollbackFor = Throwable.class)
  public void bindRoleModuleToUser(Long userId, List<ERole> eRoleList) {
    bindRoleToUser(
        userId,
        roleRepository
            .selectByRoleCodeIn(eRoleList.stream().map(Enum::name).collect(Collectors.toList()))
            .stream()
            .map(Role::getId)
            .toList());
  }

  private void setCurrentRolePermission(RoleDto roleDto, List<Record> roleResult) {
    if (roleResult.get(0).getValue(PERMISSION.ID) != null) {
      roleResult.forEach(
          (record) -> {
            PermissionDto permissionDto = createRbacDtoPermissionPart(record);
            roleDto.getPermissions().add(permissionDto);
          });
    }
  }

  private PermissionDto createRbacDtoPermissionPart(Record record) {
    PermissionDto permissionDto = new PermissionDto();
    permissionDto.setId(record.getValue(PERMISSION.ID));
    permissionDto.setCode(record.getValue(PERMISSION.CODE));
    permissionDto.setName(record.getValue(PERMISSION.NAME));
    return permissionDto;
  }

  private RoleDto createRbacDtoRolePart(List<Record> roleResult) {
    RoleDto roleDto = new RoleDto();
    roleDto.setId(roleResult.get(0).getValue(ROLE.ID));
    roleDto.setCode(roleResult.get(0).getValue(ROLE.CODE));
    roleDto.setName(roleResult.get(0).getValue(ROLE.NAME));
    return roleDto;
  }

  public boolean isRoleDuplicate(String roleCode, String name) {
    return roleRepository.fetchOneByCode(roleCode) != null
        || roleRepository.fetchOneByName(name) != null;
  }

  public boolean isUsernameDuplicate(String username) {
    return userRepository.fetchOneByUsername(username) != null;
  }

  public boolean isPermissionDuplicate(String code, String name) {
    return permissionRepository.fetchOneByCode(code) != null
        || permissionRepository.fetchOneByName(name) != null;
  }
}
