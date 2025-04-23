package com.zl.mjga.integration.quartz;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import com.zl.mjga.job.EmailJob;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Disabled
public class SendEmailJobTest extends AbstractQuartzTest {
  private Boolean executed;

  @Autowired
  @Qualifier("emailJobSchedulerFactory") private SchedulerFactoryBean emailJobSchedulerFactory;

  @Test
  public void emailJobScheduler_givenDynamicJobAndTrigger_shouldRunJobAtDesiredTime()
      throws Exception {
    Scheduler emailJobScheduler = emailJobSchedulerFactory.getScheduler();
    assertTrue(emailJobScheduler.isStarted());
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("userEmail", "Gh273@gmail.com");

    JobDetail jobDetail =
        newJob(EmailJob.class)
            .withIdentity("email-job", "customer-service")
            .usingJobData(jobDataMap)
            .build();

    Trigger trigger =
        newTrigger()
            .withIdentity("email-trigger", "customer-service")
            .startAt(futureDate(1, DateBuilder.IntervalUnit.SECOND))
            .build();

    emailJobScheduler
        .getListenerManager()
        .addJobListener(
            new JobListener() {
              @Override
              public String getName() {
                return "TestJobListener";
              }

              @Override
              public void jobToBeExecuted(JobExecutionContext context) {}

              @Override
              public void jobExecutionVetoed(JobExecutionContext context) {}

              @Override
              public void jobWasExecuted(
                  JobExecutionContext context, JobExecutionException jobException) {
                executed = Boolean.TRUE;
              }
            });
    emailJobScheduler.scheduleJob(jobDetail, trigger);
    assertTrue(emailJobScheduler.isStarted());
    await()
        .atMost(3, java.util.concurrent.TimeUnit.SECONDS)
        .untilAsserted(() -> assertThat(executed).isTrue());
  }
}
