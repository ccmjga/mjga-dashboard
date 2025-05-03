package com.zl.mjga.dto.department;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record DepartmentBindDto(@NotNull Long userId, @NotNull List<Long> departmentIds) {}
