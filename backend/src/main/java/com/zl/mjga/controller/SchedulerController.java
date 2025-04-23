package com.zl.mjga.controller;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.PageResponseDto;
import com.zl.mjga.dto.scheduler.JobTriggerDto;
import com.zl.mjga.service.SchedulerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class SchedulerController {

  private final SchedulerService schedulerService;

  @GetMapping("/jobs")
  public PageResponseDto<List<JobTriggerDto>> jobs(@ModelAttribute PageRequestDto pageRequestDto) {
    return schedulerService.getJobWithTriggerBy(pageRequestDto);
  }
}
