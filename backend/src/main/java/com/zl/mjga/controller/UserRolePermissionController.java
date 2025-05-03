package com.zl.mjga.controller;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.PageResponseDto;
import com.zl.mjga.dto.department.DepartmentBindDto;
import com.zl.mjga.dto.urp.*;
import com.zl.mjga.repository.RoleRepository;
import com.zl.mjga.repository.UserRepository;
import com.zl.mjga.service.UserRolePermissionService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.generated.mjga.tables.pojos.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RestController
@RequestMapping("/urp")
@RequiredArgsConstructor
public class UserRolePermissionController {

  private final UserRolePermissionService userRolePermissionService;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @GetMapping("/me")
  UserRolePermissionDto currentUser(Principal principal) {
    String name = principal.getName();
    User user = userRepository.fetchOneByUsername(name);
    return userRolePermissionService.queryUniqueUserWithRolePermission(user.getId());
  }

  @PostMapping("/me")
  void upsertMe(Principal principal, @RequestBody UserUpsertDto userUpsertDto) {
    String name = principal.getName();
    User user = userRepository.fetchOneByUsername(name);
    userUpsertDto.setId(user.getId());
    userRolePermissionService.upsertUser(userUpsertDto);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_USER_ROLE_PERMISSION)")
  @PostMapping("/user")
  void upsertUser(@RequestBody UserUpsertDto userUpsertDto) {
    userRolePermissionService.upsertUser(userUpsertDto);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).READ_USER_ROLE_PERMISSION)")
  @GetMapping("/user")
  UserRolePermissionDto queryUserWithRolePermission(@RequestParam Long userId) {
    return userRolePermissionService.queryUniqueUserWithRolePermission(userId);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_USER_ROLE_PERMISSION)")
  @DeleteMapping("/user")
  void deleteUser(@RequestParam Long userId) {
    userRepository.deleteById(userId);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_USER_ROLE_PERMISSION)")
  @PostMapping("/role")
  void upsertRole(@RequestBody RoleUpsertDto roleUpsertDto) {
    userRolePermissionService.upsertRole(roleUpsertDto);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_USER_ROLE_PERMISSION)")
  @DeleteMapping("/role")
  void deleteRole(@RequestParam Long roleId) {
    roleRepository.deleteById(roleId);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_USER_ROLE_PERMISSION)")
  @GetMapping("/role")
  RoleDto queryRoleWithPermission(@RequestParam Long roleId) {
    return userRolePermissionService.queryUniqueRoleWithPermission(roleId);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_USER_ROLE_PERMISSION)")
  @PostMapping("/permission")
  void upsertPermission(@RequestBody PermissionUpsertDto permissionUpsertDto) {
    userRolePermissionService.upsertPermission(permissionUpsertDto);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_USER_ROLE_PERMISSION)")
  @DeleteMapping("/permission")
  void deletePermission(@RequestParam Long permissionId) {
    roleRepository.deleteById(permissionId);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).READ_USER_ROLE_PERMISSION)")
  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  PageResponseDto<List<UserRolePermissionDto>> queryUsers(
      @ModelAttribute PageRequestDto pageRequestDto, @ModelAttribute UserQueryDto userQueryDto) {
    return userRolePermissionService.pageQueryUser(pageRequestDto, userQueryDto);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).READ_USER_ROLE_PERMISSION)")
  @GetMapping("/roles")
  @ResponseStatus(HttpStatus.OK)
  PageResponseDto<List<RoleDto>> queryRoles(
      @ModelAttribute PageRequestDto pageRequestDto, @ModelAttribute RoleQueryDto roleQueryDto) {
    return userRolePermissionService.pageQueryRole(pageRequestDto, roleQueryDto);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).READ_USER_ROLE_PERMISSION)")
  @GetMapping("/permissions")
  @ResponseStatus(HttpStatus.OK)
  PageResponseDto<List<PermissionDto>> queryPermissions(
      @ModelAttribute PageRequestDto pageRequestDto,
      @ModelAttribute PermissionQueryDto permissionQueryDto) {
    return userRolePermissionService.pageQueryPermission(pageRequestDto, permissionQueryDto);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_USER_ROLE_PERMISSION)")
  @PostMapping("/users/{userId}/bind-role")
  @ResponseStatus(HttpStatus.OK)
  void bindRoleToUser(@PathVariable Long userId, @RequestBody List<Long> roleIdList) {
    List<Long> filtedRoleIdList =
        userRolePermissionService.removeDuplicateRoleId(userId, roleIdList);
    if (CollectionUtils.isNotEmpty(filtedRoleIdList)) {
      userRolePermissionService.bindRoleToUser(userId, filtedRoleIdList);
    }
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_USER_ROLE_PERMISSION)")
  @PostMapping("/users/{userId}/unbind-role")
  @ResponseStatus(HttpStatus.OK)
  void unBindRoleToUser(@PathVariable Long userId, @RequestBody List<Long> roleIdList) {
    userRolePermissionService.unBindRoleToUser(userId, roleIdList);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_USER_ROLE_PERMISSION)")
  @PostMapping("/roles/{roleId}/bind-permission")
  @ResponseStatus(HttpStatus.OK)
  void bindPermissionToRole(@PathVariable Long roleId, @RequestBody List<Long> permissionIdList) {
    List<Long> filtedPermissionIdList =
        userRolePermissionService.removeDuplicatePermissionId(roleId, permissionIdList);
    if (CollectionUtils.isNotEmpty(filtedPermissionIdList)) {
      userRolePermissionService.bindPermissionToRole(roleId, filtedPermissionIdList);
    }
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_USER_ROLE_PERMISSION)")
  @PostMapping("/roles/{roleId}/unbind-permission")
  @ResponseStatus(HttpStatus.OK)
  void unBindPermissionToRole(@PathVariable Long roleId, @RequestBody List<Long> permissionIdList) {
    userRolePermissionService.unBindPermissionToRole(roleId, permissionIdList);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_DEPARTMENT_PERMISSION)")
  @PostMapping("/department/bind")
  @ResponseStatus(HttpStatus.OK)
  public void bindDepartmentBy(@RequestBody DepartmentBindDto departmentBindDto) {
    userRolePermissionService.bindDepartmentBy(departmentBindDto);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_DEPARTMENT_PERMISSION)")
  @PostMapping("/department/unbind")
  @ResponseStatus(HttpStatus.OK)
  public void unBindDepartmentBy(@RequestBody DepartmentBindDto departmentBindDto) {
    userRolePermissionService.unBindDepartmentBy(departmentBindDto);
  }
}
