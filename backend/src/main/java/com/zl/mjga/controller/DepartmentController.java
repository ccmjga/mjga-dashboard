package com.zl.mjga.controller;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.PageResponseDto;
import com.zl.mjga.dto.department.DepartmentQueryDto;
import com.zl.mjga.dto.department.DepartmentUpsertDto;
import com.zl.mjga.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.generated.mjga.tables.pojos.Department;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

  private final DepartmentService departmentService;

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).READ_DEPARTMENT_PERMISSION)")
  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  PageResponseDto<List<Department>> pageQueryDepartments(
      @ModelAttribute PageRequestDto pageRequestDto,
      @ModelAttribute DepartmentQueryDto departmentQueryDto) {
    return departmentService.pageQueryDepartment(pageRequestDto, departmentQueryDto);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_DEPARTMENT_PERMISSION)")
  @DeleteMapping("")
  void deleteDepartment(@RequestParam Long id) {
    departmentService.deleteDepartment(id);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_DEPARTMENT_PERMISSION)")
  @PostMapping()
  void upsertDepartment(@RequestBody DepartmentUpsertDto departmentUpsertDto) {
    departmentService.upsertDepartment(departmentUpsertDto);
  }
}
