package com.zl.mjga.repository;

import static org.jooq.generated.mjga.Tables.*;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.noField;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.position.PositionQueryDto;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.generated.mjga.tables.daos.PositionDao;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PositionRepository extends PositionDao {

  @Autowired
  public PositionRepository(Configuration configuration) {
    super(configuration);
  }

  public Result<Record> pageFetchBy(
      PageRequestDto pageRequestDto, PositionQueryDto positionQueryDto) {
    return ctx()
        .select(
            POSITION.asterisk(),
            positionQueryDto.getUserId() != null
                ? DSL.when(POSITION.ID.in(selectUsersPosition(positionQueryDto.getUserId())), true)
                    .otherwise(false)
                    .as("is_bound")
                : noField(),
            DSL.count().over().as("total_position").convertFrom(Long::valueOf))
        .from(POSITION)
        .where(
            switch (positionQueryDto.getBindState()) {
              case BIND -> POSITION.ID.in(selectUsersPosition(positionQueryDto.getUserId()));
              case UNBIND -> POSITION.ID.notIn(selectUsersPosition(positionQueryDto.getUserId()));
              case ALL -> noCondition();
            })
        .and(
            StringUtils.isNotEmpty(positionQueryDto.getName())
                ? POSITION.NAME.like("%" + positionQueryDto.getName() + "%")
                : noCondition())
        .orderBy(pageRequestDto.getSortFields())
        .limit(pageRequestDto.getSize())
        .offset(pageRequestDto.getOffset())
        .fetch();
  }

  private SelectConditionStep<Record1<Long>> selectUsersPosition(Long userId) {
    return ctx()
        .select(USER.position().ID)
        .from(USER)
        .leftJoin(USER.position())
        .where(USER.ID.eq(userId));
  }
}
