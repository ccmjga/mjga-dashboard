package com.zl.mjga.dto.department;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentQueryDto {
  private String name;
  private Boolean enable;
}
