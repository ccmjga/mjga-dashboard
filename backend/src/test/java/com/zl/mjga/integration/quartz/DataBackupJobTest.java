package com.zl.mjga.integration.quartz;

import static org.junit.jupiter.api.Assertions.*;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class DataBackupJobTest extends AbstractQuartzTest {
  @Autowired
  @Qualifier("dataBackupJobDetail") private JobDetail dataBackupJobDetail;

  @Autowired
  @Qualifier("dataBackupTrigger") private CronTriggerFactoryBean dataBackupTrigger;

  @Autowired
  @Qualifier("dataBackupSchedulerFactory") private SchedulerFactoryBean dataBackupSchedulerFactory;

  @Test
  public void dataBackupJobDetail_defineShouldValid_descShouldEqual() {
    assertNotNull(dataBackupJobDetail);
    assertEquals("data-backup-job", dataBackupJobDetail.getKey().getName());
    assertEquals("batch-service", dataBackupJobDetail.getKey().getGroup());
    assertTrue(dataBackupJobDetail.isDurable());
    assertEquals("Gh2mxa", dataBackupJobDetail.getJobDataMap().getString("roleId"));
  }

  @Test
  public void dataBackupTrigger_defineShouldValid_cronShouldBeTriggeredAtDesiredTime() {
    assertNotNull(dataBackupTrigger);
    CronTrigger cronTrigger = dataBackupTrigger.getObject();
    assertNotNull(cronTrigger);
    assertEquals("0 0/5 * * * ?", cronTrigger.getCronExpression());
    try {
      CronExpression cron = new CronExpression(cronTrigger.getCronExpression());
      Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-10-30 11:25:00");
      Date nextValidTimeAfter = cron.getNextValidTimeAfter(now);

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(now);
      calendar.add(Calendar.MINUTE, 5);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      Date expectedNextTriggerTime = calendar.getTime();
      assertEquals(expectedNextTriggerTime, nextValidTimeAfter);
    } catch (ParseException e) {
      fail(MessageFormat.format("Invalid cron expression {0}", e));
    }
  }

  @Test
  public void dataBackupSchedulerFactory_defineShouldValid_descShouldEqual()
      throws SchedulerException {
    assertNotNull(dataBackupSchedulerFactory);
    JobKey jobKey =
        JobKey.jobKey(
            dataBackupJobDetail.getKey().getName(), dataBackupJobDetail.getKey().getGroup());
    assertEquals(
        dataBackupSchedulerFactory.getScheduler().getJobDetail(jobKey), dataBackupJobDetail);
    TriggerKey triggerKey = Objects.requireNonNull(dataBackupTrigger.getObject()).getKey();
    assertEquals(
        dataBackupSchedulerFactory.getScheduler().getTrigger(triggerKey),
        dataBackupTrigger.getObject());
    assertEquals(
        "data-backup-scheduler", dataBackupSchedulerFactory.getScheduler().getSchedulerName());
  }

  @Test
  public void dataBackupScheduler_startShouldSuccess() throws SchedulerException {
    Scheduler scheduler = dataBackupSchedulerFactory.getScheduler();
    assertNotNull(scheduler);
    scheduler.start();
    assertTrue(scheduler.isStarted());
  }
}
