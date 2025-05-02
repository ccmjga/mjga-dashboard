package com.zl.mjga.service;

import com.zl.mjga.dto.PageRequestDto;
import com.zl.mjga.dto.PageResponseDto;
import com.zl.mjga.dto.department.DepartmentQueryDto;
import com.zl.mjga.dto.department.DepartmentUpsertDto;
import com.zl.mjga.repository.DepartmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.generated.mjga.tables.pojos.Department;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentService {

  private final DepartmentRepository departmentRepository;

  public PageResponseDto<List<Department>> pageQueryDepartment(
      PageRequestDto pageRequestDto, DepartmentQueryDto departmentQueryDto) {
    Result<Record> records = departmentRepository.pageFetchBy(pageRequestDto, departmentQueryDto);
    List<Department> departments = records.into(Department.class);
    Long totalDepartment = records.get(0).getValue("total_department", Long.class);
    return new PageResponseDto<>(totalDepartment, departments);
  }

  public void deleteDepartment(Long id) {
    departmentRepository.deleteById(id);
  }

  public void upsertDepartment(DepartmentUpsertDto departmentUpsertDto) {
    Department department = new Department();
    BeanUtils.copyProperties(departmentUpsertDto, department);
    departmentRepository.merge(department);
  }
}
