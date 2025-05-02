package com.zl.mjga.repository;

import static org.jooq.generated.public_.Tables.*;
import static org.jooq.impl.DSL.*;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.scheduler.JobTriggerNestedDto;
import com.zl.mjga.dto.scheduler.TriggerDto;
import java.util.List;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.generated.public_.tables.daos.QrtzJobDetailsDao;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QrtzJobRepository extends QrtzJobDetailsDao {

  @Autowired
  public QrtzJobRepository(Configuration configuration) {
    super(configuration);
  }

  public Result<Record> fetchPageWithJobAndTriggerBy(PageRequestDto pageRequestDto) {
    return ctx()
        .select(asterisk(), DSL.count().over().as("total_job"))
        .from(QRTZ_JOB_DETAILS)
        .leftJoin(QRTZ_TRIGGERS)
        .on(
            QRTZ_JOB_DETAILS
                .SCHED_NAME
                .eq(QRTZ_TRIGGERS.SCHED_NAME)
                .and(QRTZ_JOB_DETAILS.JOB_NAME.eq(QRTZ_TRIGGERS.JOB_NAME))
                .and(QRTZ_JOB_DETAILS.JOB_GROUP.eq(QRTZ_TRIGGERS.JOB_GROUP)))
        .leftJoin(QRTZ_SIMPLE_TRIGGERS)
        .on(
            QRTZ_TRIGGERS
                .SCHED_NAME
                .eq(QRTZ_CRON_TRIGGERS.SCHED_NAME)
                .and(QRTZ_TRIGGERS.TRIGGER_NAME.eq(QRTZ_CRON_TRIGGERS.TRIGGER_NAME))
                .and(QRTZ_TRIGGERS.TRIGGER_GROUP.eq(QRTZ_CRON_TRIGGERS.TRIGGER_GROUP)))
        .orderBy(pageRequestDto.getSortFields())
        .limit(pageRequestDto.getSize())
        .offset(pageRequestDto.getOffset())
        .fetch();
  }

  public List<JobTriggerNestedDto> fetchPageWithJobAndTriggerTreeBy(PageRequestDto pageRequestDto) {
    return ctx()
        .select(
            QRTZ_JOB_DETAILS.asterisk(),
            multiset(
                    select(
                            QRTZ_TRIGGERS.TRIGGER_NAME,
                            QRTZ_TRIGGERS.TRIGGER_GROUP,
                            QRTZ_TRIGGERS.DESCRIPTION,
                            QRTZ_TRIGGERS.NEXT_FIRE_TIME,
                            QRTZ_TRIGGERS.PREV_FIRE_TIME,
                            QRTZ_TRIGGERS.START_TIME,
                            QRTZ_TRIGGERS.END_TIME,
                            QRTZ_TRIGGERS.TRIGGER_STATE,
                            QRTZ_TRIGGERS.TRIGGER_TYPE)
                        .from(QRTZ_TRIGGERS))
                .as("triggers")
                .convertFrom(
                    result ->
                        result.map(
                            (record) -> {
                              TriggerDto triggerDto = new TriggerDto();
                              triggerDto.setName(record.getValue(QRTZ_TRIGGERS.TRIGGER_NAME));
                              triggerDto.setGroup(record.getValue(QRTZ_TRIGGERS.TRIGGER_GROUP));
                              triggerDto.setNextFireTime(
                                  record.getValue(QRTZ_TRIGGERS.NEXT_FIRE_TIME));
                              triggerDto.setStartTime(record.getValue(QRTZ_TRIGGERS.START_TIME));
                              triggerDto.setEndTime(record.getValue(QRTZ_TRIGGERS.END_TIME));
                              triggerDto.setPreviousFireTime(
                                  record.getValue(QRTZ_TRIGGERS.PREV_FIRE_TIME));
                              triggerDto.setSchedulerType(
                                  record.getValue(QRTZ_TRIGGERS.TRIGGER_TYPE));
                              return triggerDto;
                            })))
        .from(QRTZ_JOB_DETAILS)
        .leftJoin(QRTZ_TRIGGERS)
        .on(
            QRTZ_JOB_DETAILS
                .SCHED_NAME
                .eq(QRTZ_TRIGGERS.SCHED_NAME)
                .and(QRTZ_JOB_DETAILS.JOB_NAME.eq(QRTZ_TRIGGERS.JOB_NAME))
                .and(QRTZ_JOB_DETAILS.JOB_GROUP.eq(QRTZ_TRIGGERS.JOB_GROUP)))
        .orderBy(pageRequestDto.getSortFields())
        .limit(pageRequestDto.getSize())
        .offset(pageRequestDto.getOffset())
        .fetch()
        .map(
            (record) -> {
              JobTriggerNestedDto jobTriggerNestedDto = new JobTriggerNestedDto();
              jobTriggerNestedDto.setName(record.getValue(QRTZ_JOB_DETAILS.JOB_NAME));
              jobTriggerNestedDto.setGroup(record.getValue(QRTZ_JOB_DETAILS.JOB_GROUP));
              jobTriggerNestedDto.setClassName(record.getValue(QRTZ_JOB_DETAILS.JOB_CLASS_NAME));
              jobTriggerNestedDto.setJobs(record.getValue("triggers", List.class));
              // TODO 差一个 total 的设置
              return jobTriggerNestedDto;
            });
  }
}
