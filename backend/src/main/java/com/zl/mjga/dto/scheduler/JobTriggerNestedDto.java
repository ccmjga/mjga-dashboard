package com.zl.mjga.dto.scheduler;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobTriggerNestedDto {
  private String name;
  private String group;
  private String className;
  private Map jobDataMap;
  private List<TriggerDto> jobs;
}
