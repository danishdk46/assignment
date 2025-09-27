package com.DepEmpManagmentSystem.assignment.Services;

import com.DepEmpManagmentSystem.assignment.DTOs.EmployeeRequestDTO;
import com.DepEmpManagmentSystem.assignment.DTOs.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO);
    EmployeeResponseDTO getEmployeeById(Long id);
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO);
    void deleteEmployee(Long id);
    List<EmployeeResponseDTO> getAllEmployees();

    // Business logic endpoint
    void adjustSalary(Long departmentId, int performanceScore);
}
