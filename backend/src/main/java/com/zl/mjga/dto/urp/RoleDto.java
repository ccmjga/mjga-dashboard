package com.zl.mjga.dto.urp;

import java.util.LinkedList;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleDto {
  private Long id;
  private String code;
  private String name;
  private Boolean isBound;
  @Builder.Default List<PermissionDto> permissions = new LinkedList<>();
}
