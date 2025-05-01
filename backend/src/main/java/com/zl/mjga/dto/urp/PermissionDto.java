package com.zl.mjga.dto.urp;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PermissionDto {
  private Long id;
  private String code;
  private String name;
}
