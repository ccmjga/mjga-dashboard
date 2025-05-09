package com.zl.mjga.dto.role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RoleBindDto(
    @NotNull(message = "用户不能为空") Long userId, @NotEmpty(message = "角色不能为空") List<Long> roleIds) {}
