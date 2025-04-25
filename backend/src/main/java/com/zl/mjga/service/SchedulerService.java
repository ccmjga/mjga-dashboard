package com.zl.mjga.service;

import static org.jooq.generated.public_.Tables.*;
import static org.quartz.TriggerBuilder.newTrigger;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.PageResponseDto;
import com.zl.mjga.dto.scheduler.JobTriggerDto;
import com.zl.mjga.repository.QrtzJobRepository;
import jakarta.annotation.Resource;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.Result;
import org.quartz.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

  @Resource(name = "emailJobSchedulerFactory")
  private Scheduler emailJobScheduler;

  @Resource(name = "dataBackupSchedulerFactory")
  private Scheduler dataBackupScheduler;

  private final QrtzJobRepository qrtzJobRepository;

  public PageResponseDto<List<JobTriggerDto>> getJobWithTriggerBy(PageRequestDto pageRequestDto) {
    Result<Record> records = qrtzJobRepository.fetchPageWithJobAndTriggerBy(pageRequestDto);
    List<JobTriggerDto> jobTriggerDtoList =
        records.map(
            record -> {
              JobTriggerDto jobTriggerDto = new JobTriggerDto();
              jobTriggerDto.setName(record.getValue(QRTZ_JOB_DETAILS.JOB_NAME));
              jobTriggerDto.setGroup(record.getValue(QRTZ_JOB_DETAILS.JOB_GROUP));
              jobTriggerDto.setClassName(record.getValue(QRTZ_JOB_DETAILS.JOB_CLASS_NAME));
              jobTriggerDto.setTriggerName(record.getValue(QRTZ_TRIGGERS.TRIGGER_NAME));
              jobTriggerDto.setTriggerGroup(record.getValue(QRTZ_TRIGGERS.TRIGGER_GROUP));
              jobTriggerDto.setCronExpression(record.getValue(QRTZ_CRON_TRIGGERS.CRON_EXPRESSION));
              jobTriggerDto.setStartTime(record.getValue(QRTZ_TRIGGERS.START_TIME));
              jobTriggerDto.setEndTime(record.getValue(QRTZ_TRIGGERS.END_TIME));
              jobTriggerDto.setNextFireTime(record.getValue(QRTZ_TRIGGERS.NEXT_FIRE_TIME));
              jobTriggerDto.setPreviousFireTime(record.getValue(QRTZ_TRIGGERS.PREV_FIRE_TIME));
              jobTriggerDto.setSchedulerType(record.getValue(QRTZ_TRIGGERS.TRIGGER_TYPE));
              jobTriggerDto.setTriggerState(record.getValue(QRTZ_TRIGGERS.TRIGGER_STATE));
              JobKey jobKey = JobKey.jobKey(jobTriggerDto.getName(), jobTriggerDto.getGroup());
              TriggerKey triggerKey =
                  TriggerKey.triggerKey(
                      jobTriggerDto.getTriggerName(), jobTriggerDto.getTriggerGroup());
              try {
                JobDetail jobDetail = emailJobScheduler.getJobDetail(jobKey);
                Trigger trigger = emailJobScheduler.getTrigger(triggerKey);
                jobTriggerDto.setJobDataMap(jobDetail.getJobDataMap());
                jobTriggerDto.setTriggerJobDataMap(trigger.getJobDataMap());
              } catch (SchedulerException e) {
                log.warn("get job or trigger data map error.", e);
              }
              return jobTriggerDto;
            });
    return new PageResponseDto<>(
        records.get(0).getValue("total_job", Integer.class), jobTriggerDtoList);
  }

  public void resumeTrigger(TriggerKey triggerKey) throws SchedulerException {
    emailJobScheduler.resumeTrigger(triggerKey);
    dataBackupScheduler.resumeTrigger(triggerKey);
  }

  public void pauseTrigger(TriggerKey triggerKey) throws SchedulerException {
    emailJobScheduler.pauseTrigger(triggerKey);
    dataBackupScheduler.pauseTrigger(triggerKey);
  }

  public void triggerJob(JobKey jobKey, Date startAt) throws SchedulerException {
    JobDetail jobDetail = emailJobScheduler.getJobDetail(jobKey);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Trigger dayLaterTrigger =
        newTrigger()
            .withIdentity(
                String.format(
                    "%s-%s-%s", "trigger", authentication.getName(), Instant.now().toEpochMilli()),
                "job-management")
            .startAt(startAt)
            .build();
    emailJobScheduler.scheduleJob(jobDetail, dayLaterTrigger);
  }

  public void updateCronTrigger(TriggerKey triggerKey, String cron) throws SchedulerException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Trigger newTrigger =
        TriggerBuilder.newTrigger()
            .withIdentity(
                String.format(
                    "%s-%s-%s",
                    "cronTrigger", authentication.getName(), Instant.now().toEpochMilli()),
                "job-management")
            .withSchedule(CronScheduleBuilder.cronSchedule(cron))
            .build();
    dataBackupScheduler.rescheduleJob(triggerKey, newTrigger);
  }
}
