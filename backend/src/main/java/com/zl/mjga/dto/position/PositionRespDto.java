package com.zl.mjga.dto.position;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PositionRespDto {
  @NotNull private Long id;
  @NotEmpty private String name;
  private Long parentId;
  private Boolean isBound;
}
