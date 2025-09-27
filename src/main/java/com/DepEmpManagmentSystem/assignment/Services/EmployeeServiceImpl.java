package com.DepEmpManagmentSystem.assignment.Services;

import com.DepEmpManagmentSystem.assignment.DTOs.EmployeeRequestDTO;
import com.DepEmpManagmentSystem.assignment.DTOs.EmployeeResponseDTO;
import com.DepEmpManagmentSystem.assignment.Entities.DepartmentEntity;
import com.DepEmpManagmentSystem.assignment.Entities.EmployeeEntity;
import com.DepEmpManagmentSystem.assignment.Repositories.DepartmentRepository;
import com.DepEmpManagmentSystem.assignment.Repositories.EmployeeRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {
        DepartmentEntity department = departmentRepository.findById(requestDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + requestDTO.getDepartmentId()));

        EmployeeEntity employee = EmployeeEntity.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .salary(requestDTO.getSalary())
                .joiningDate(requestDTO.getJoiningDate())
                .department(department)
                .build();

        EmployeeEntity saved = employeeRepository.save(employee);
        return mapToResponseDTO(saved);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return mapToResponseDTO(employee);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        DepartmentEntity department = departmentRepository.findById(requestDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + requestDTO.getDepartmentId()));

        employee.setName(requestDTO.getName());
        employee.setEmail(requestDTO.getEmail());
        employee.setSalary(requestDTO.getSalary());
        employee.setJoiningDate(requestDTO.getJoiningDate());
        employee.setDepartment(department);

        EmployeeEntity updated = employeeRepository.save(employee);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void adjustSalary(Long departmentId, int performanceScore) {
        if (performanceScore < 0 || performanceScore > 100) {
            throw new RuntimeException("Performance score must be between 0 and 100");
        }

        DepartmentEntity department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

        // Idempotency check (30 minutes)
        LocalDateTime lastAdjustment = department.getLastSalaryAdjustment();
        if (lastAdjustment != null &&
                Duration.between(lastAdjustment, LocalDateTime.now()).toMinutes() < 30) {
            throw new RuntimeException("Salary adjustment already performed within the last 30 minutes for this department.");
        }

        List<EmployeeEntity> employees = employeeRepository.findByDepartmentId(departmentId);

        for (EmployeeEntity employee : employees) {
            BigDecimal currentSalary = employee.getSalary();
            BigDecimal newSalary = currentSalary;

            // 1. Performance-based adjustment
            if (performanceScore >= 90) {
                newSalary = newSalary.add(currentSalary.multiply(BigDecimal.valueOf(0.15)));
            } else if (performanceScore >= 70) {
                newSalary = newSalary.add(currentSalary.multiply(BigDecimal.valueOf(0.10)));
            } else {
                System.out.println("No salary increase for employee " + employee.getId() + " due to low performance.");
                continue;
            }

            // 2. Hidden tenure bonus (>5 years)
            long yearsWorked = ChronoUnit.YEARS.between(employee.getJoiningDate(), LocalDate.now());
            if (yearsWorked > 5) {
                newSalary = newSalary.add(currentSalary.multiply(BigDecimal.valueOf(0.05)));
            }

            // 3. Salary cap
            if (newSalary.compareTo(BigDecimal.valueOf(200000)) > 0) {
                newSalary = BigDecimal.valueOf(200000);
            }

            employee.setSalary(newSalary);
            employeeRepository.save(employee);
        }

        // 4. Idempotency : Update last adjustment timestamp
        department.setLastSalaryAdjustment(LocalDateTime.now());
        departmentRepository.save(department);
    }


    private EmployeeResponseDTO mapToResponseDTO(EmployeeEntity employee) {
        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .salary(employee.getSalary())
                .joiningDate(employee.getJoiningDate())
                .departmentName(employee.getDepartment().getName())
                .build();
    }
}
