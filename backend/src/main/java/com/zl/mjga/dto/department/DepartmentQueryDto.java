package com.zl.mjga.dto.department;

import com.zl.mjga.model.urp.BindState;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentQueryDto {
  private Long userId;
  private String name;
  private Boolean enable;
  private BindState bindState = BindState.ALL;
}
