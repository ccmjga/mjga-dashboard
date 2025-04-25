package com.zl.mjga.controller;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.PageResponseDto;
import com.zl.mjga.dto.scheduler.JobTriggerDto;
import com.zl.mjga.service.SchedulerService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class SchedulerController {

  private final SchedulerService schedulerService;

  @GetMapping("/jobs")
  public PageResponseDto<List<JobTriggerDto>> jobs(@ModelAttribute PageRequestDto pageRequestDto) {
    return schedulerService.getJobWithTriggerBy(pageRequestDto);
  }

  @PostMapping("/trigger/resume")
  public void resumeTrigger(@RequestBody TriggerKey triggerKey) throws SchedulerException {
    schedulerService.resumeTrigger(triggerKey);
  }

  @PostMapping("/trigger/pause")
  public void pauseJob(@RequestBody TriggerKey triggerKey) throws SchedulerException {
    schedulerService.pauseTrigger(triggerKey);
  }

  @PostMapping("/job/trigger")
  public void triggerJob(@RequestBody JobKey jobKey, @RequestParam Long startAt)
      throws SchedulerException {
    schedulerService.triggerJob(jobKey, new Date(startAt));
  }

  @PostMapping("/job/update")
  public void triggerJob(@RequestBody TriggerKey triggerKey, @RequestParam String cron)
      throws SchedulerException {
    schedulerService.updateCronTrigger(triggerKey, cron);
  }
}
