package com.zl.mjga.controller;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.PageResponseDto;
import com.zl.mjga.dto.position.PositionQueryDto;
import com.zl.mjga.dto.position.PositionRespDto;
import com.zl.mjga.repository.PositionRepository;
import com.zl.mjga.service.PositionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.generated.mjga.tables.pojos.Position;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RestController
@RequestMapping("/position")
@RequiredArgsConstructor
public class PositionController {

  private final PositionService positionService;
  private final PositionRepository positionRepository;

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).READ_DEPARTMENT_PERMISSION)")
  @GetMapping("/page-query")
  @ResponseStatus(HttpStatus.OK)
  PageResponseDto<List<PositionRespDto>> pageQueryPositions(
      @ModelAttribute PageRequestDto pageRequestDto,
      @ModelAttribute PositionQueryDto positionQueryDto) {
    return positionService.pageQueryPosition(pageRequestDto, positionQueryDto);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).READ_DEPARTMENT_PERMISSION)")
  @GetMapping("/query")
  List<Position> queryPositions() {
    return positionRepository.findAll();
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_DEPARTMENT_PERMISSION)")
  @DeleteMapping()
  void deletePosition(@RequestParam Long id) {
    positionRepository.deleteById(id);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_DEPARTMENT_PERMISSION)")
  @PostMapping()
  void upsertPosition(@RequestBody Position position) {
    positionRepository.merge(position);
  }
}
