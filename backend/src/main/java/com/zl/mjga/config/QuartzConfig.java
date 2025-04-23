package com.zl.mjga.config;

import com.zl.mjga.job.DataBackupJob;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

  @Value("${spring.flyway.default-schema}")
  private String defaultSchema;

  @Bean("emailJobSchedulerFactory")
  public SchedulerFactoryBean emailJobSchedulerFactory(DataSource dataSource) {
    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
    schedulerFactory.setSchedulerName("email-scheduler");
    schedulerFactory.setDataSource(dataSource);
    Properties props = getCommonProps();
    props.setProperty("org.quartz.threadPool.threadCount", "10");
    schedulerFactory.setQuartzProperties(props);
    return schedulerFactory;
  }

  @Bean("dataBackupJobDetail")
  public JobDetailFactoryBean dataBackupJobDetail() {
    JobDetailFactoryBean factory = new JobDetailFactoryBean();
    factory.setJobClass(DataBackupJob.class);
    factory.setJobDataMap(new JobDataMap(Map.of("userId", "Gh2mxa")));
    factory.setName("data-backup-job");
    factory.setGroup("batch-service");
    factory.setDurability(true);
    return factory;
  }

  @Bean("dataBackupSchedulerFactory")
  public SchedulerFactoryBean dataBackupSchedulerFactory(
      Trigger dataBackupTrigger, JobDetail dataBackupJobDetail, DataSource dataSource) {
    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
    schedulerFactory.setSchedulerName("data-backup-scheduler");
    Properties props = getCommonProps();
    props.setProperty("org.quartz.threadPool.threadCount", "10");
    schedulerFactory.setQuartzProperties(props);
    schedulerFactory.setJobDetails(dataBackupJobDetail);
    schedulerFactory.setTriggers(dataBackupTrigger);
    schedulerFactory.setDataSource(dataSource);

    return schedulerFactory;
  }

  private Properties getCommonProps() {
    Properties props = new Properties();
    props.setProperty(
        "org.quartz.jobStore.class",
        "org.springframework.scheduling.quartz.LocalDataSourceJobStore");
    props.setProperty(
        "org.quartz.jobStore.driverDelegateClass",
        "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
    props.setProperty("org.quartz.jobStore.tablePrefix", String.format("%s.qrtz_", defaultSchema));
    return props;
  }

  @Bean("dataBackupTrigger")
  public CronTriggerFactoryBean dataBackupTrigger(JobDetail dataBackupJobDetail) {
    CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
    factory.setJobDetail(dataBackupJobDetail);
    factory.setCronExpression("0 0/5 * * * ?");
    return factory;
  }
}
