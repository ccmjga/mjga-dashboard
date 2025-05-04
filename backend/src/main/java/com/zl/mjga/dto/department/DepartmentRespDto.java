package com.zl.mjga.dto.department;

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
public class DepartmentRespDto {
  @NotNull private Long id;
  @NotEmpty private String name;
  private Long parentId;
  private String parentName;
  private Boolean isBound;
}
