package com.zl.mjga.integration.quartz;

import static org.junit.jupiter.api.Assertions.*;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import com.zl.mjga.job.EmailJob;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class TaskManagementTest extends AbstractQuartzTest {

  @Autowired
  @Qualifier("emailJobSchedulerFactory") private SchedulerFactoryBean emailJobSchedulerFactory;

  @Autowired
  @Qualifier("dataBackupSchedulerFactory") private SchedulerFactoryBean dataBackupSchedulerFactory;

  @Autowired
  @Qualifier("dataBackupTrigger") private CronTriggerFactoryBean dataBackupTrigger;

  @Autowired
  @Qualifier("dataBackupJobDetail") private JobDetailFactoryBean dataBackupJobDetail;

  @Test
  void crudTask_interactWithScheduler_shouldManipulateAllTask() throws SchedulerException {
    Scheduler dataBackupScheduler = dataBackupSchedulerFactory.getScheduler();
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("userEmail", "Gh273@gmail.com");

    // trigger job
    JobDetail jobDetail =
        newJob(EmailJob.class)
            .withIdentity("email-job", "customer-service")
            .usingJobData(jobDataMap)
            .build();
    Trigger dayLaterTrigger =
        newTrigger()
            .withIdentity("email-trigger", "customer-service")
            .startAt(futureDate(1, DateBuilder.IntervalUnit.DAY))
            .build();
    Scheduler emailJobScheduler = emailJobSchedulerFactory.getScheduler();
    emailJobScheduler.scheduleJob(jobDetail, dayLaterTrigger);

    // list all jobs with k(name):v(group)
    Set<JobKey> emailJobKeys = emailJobScheduler.getJobKeys(GroupMatcher.anyJobGroup());
    Set<JobKey> dataBackupJobKeys = dataBackupScheduler.getJobKeys(GroupMatcher.anyJobGroup());

    assertEquals(emailJobKeys.size(), 1);
    assertEquals(dataBackupJobKeys.size(), 1);

    // get job's trigger details
    JobKey firstEmailJobKey = emailJobKeys.iterator().next();
    JobDetail existJobDetail = emailJobScheduler.getJobDetail(firstEmailJobKey);
    assertEquals(existJobDetail.getJobClass(), EmailJob.class);
    List<? extends Trigger> triggersOfJob = emailJobScheduler.getTriggersOfJob(firstEmailJobKey);
    Trigger firstTrigger = triggersOfJob.get(0);
    assertNotNull(firstTrigger.getNextFireTime());
    assertNotNull(firstTrigger.getStartTime());
    JobDataMap jobDataMap1 = firstTrigger.getJobDataMap();
    // pause & resume job
    JobKey firstDataBackupJobKey = dataBackupJobKeys.iterator().next();
    assertDoesNotThrow(
        () -> {
          dataBackupScheduler.pauseJob(firstDataBackupJobKey);
          dataBackupScheduler.resumeJob(firstDataBackupJobKey);
        });

    // update job
    TriggerKey oldDataBackupTriggerKey =
        Objects.requireNonNull(dataBackupTrigger.getObject()).getKey();
    Trigger newTrigger =
        TriggerBuilder.newTrigger()
            .withIdentity(oldDataBackupTriggerKey)
            .withSchedule(CronScheduleBuilder.cronSchedule("0 0/6 * * * ?"))
            .build();
    dataBackupScheduler.rescheduleJob(oldDataBackupTriggerKey, newTrigger);
    assertEquals(
        dataBackupScheduler.getTriggersOfJob(firstDataBackupJobKey).get(0).getKey(),
        newTrigger.getKey());
  }
}
