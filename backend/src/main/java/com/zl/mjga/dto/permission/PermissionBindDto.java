package com.zl.mjga.dto.permission;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PermissionBindDto(
    @NotNull Long roleId, @NotEmpty(message = "权限不能为空") List<Long> permissionIds) {}
