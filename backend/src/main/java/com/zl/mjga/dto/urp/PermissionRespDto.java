package com.zl.mjga.dto.urp;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PermissionRespDto {
  private Long id;
  private String code;
  private String name;
  private Boolean isBound;
}
