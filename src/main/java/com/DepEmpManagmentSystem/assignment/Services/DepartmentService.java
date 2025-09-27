package com.DepEmpManagmentSystem.assignment.Services;

import com.DepEmpManagmentSystem.assignment.DTOs.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO getDepartmentById(Long id);
    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO);
    void deleteDepartment(Long id);
    List<DepartmentDTO> getAllDepartments();
}
