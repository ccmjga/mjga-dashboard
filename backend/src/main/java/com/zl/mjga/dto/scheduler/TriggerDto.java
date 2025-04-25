package com.zl.mjga.dto.scheduler;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TriggerDto {
  private String name;
  private String group;
  private String schedulerType;
  private String cronExpression;
  private long startTime;
  private long endTime;
  private long nextFireTime;
  private long previousFireTime;
  private Map jobDataMap;
}
