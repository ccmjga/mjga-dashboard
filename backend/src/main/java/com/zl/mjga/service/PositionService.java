package com.zl.mjga.service;

import static org.jooq.generated.mjga.tables.Position.POSITION;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.PageResponseDto;
import com.zl.mjga.dto.position.PositionQueryDto;
import com.zl.mjga.dto.position.PositionRespDto;
import com.zl.mjga.repository.PositionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PositionService {

  private final PositionRepository positionRepository;

  public PageResponseDto<List<PositionRespDto>> pageQueryPosition(
      PageRequestDto pageRequestDto, PositionQueryDto positionQueryDto) {
    Result<Record> records = positionRepository.pageFetchBy(pageRequestDto, positionQueryDto);
    if (records.isEmpty()) {
      return PageResponseDto.empty();
    }
    List<PositionRespDto> positions =
        records.map(
            record ->
                PositionRespDto.builder()
                    .id(record.getValue(POSITION.ID))
                    .name(record.getValue(POSITION.NAME))
                    .isBound(
                        record.field("is_bound", Boolean.class) != null
                            ? record.getValue("is_bound", Boolean.class)
                            : null)
                    .build());
    Long totalPosition = records.get(0).getValue("total_position", Long.class);
    return new PageResponseDto<>(totalPosition, positions);
  }
}
