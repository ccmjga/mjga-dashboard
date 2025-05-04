package com.zl.mjga.dto.position;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PositionBindDto(@NotNull Long userId, @NotNull List<Long> positionIds) {}
