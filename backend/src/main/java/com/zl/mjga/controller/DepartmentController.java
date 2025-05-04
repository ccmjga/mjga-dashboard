package com.zl.mjga.controller;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.PageResponseDto;
import com.zl.mjga.dto.department.DepartmentQueryDto;
import com.zl.mjga.dto.department.DepartmentRespDto;
import com.zl.mjga.repository.DepartmentRepository;
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
  private final DepartmentRepository departmentRepository;

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).READ_DEPARTMENT_PERMISSION)")
  @GetMapping("/page-query")
  @ResponseStatus(HttpStatus.OK)
  PageResponseDto<List<DepartmentRespDto>> pageQueryDepartments(
      @ModelAttribute PageRequestDto pageRequestDto,
      @ModelAttribute DepartmentQueryDto departmentQueryDto) {
    return departmentService.pageQueryDepartment(pageRequestDto, departmentQueryDto);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).READ_DEPARTMENT_PERMISSION)")
  @GetMapping("/query")
  List<Department> queryDepartments() {
    return departmentRepository.findAll();
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_DEPARTMENT_PERMISSION)")
  @DeleteMapping()
  void deleteDepartment(@RequestParam Long id) {
    departmentRepository.deleteById(id);
  }

  @PreAuthorize("hasAuthority(T(com.zl.mjga.model.urp.EPermission).WRITE_DEPARTMENT_PERMISSION)")
  @PostMapping()
  void upsertDepartment(@RequestBody Department department) {
    departmentRepository.merge(department);
  }
}
