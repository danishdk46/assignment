package com.DepEmpManagmentSystem.assignment.Controllers;

import com.DepEmpManagmentSystem.assignment.DTOs.EmployeeRequestDTO;
import com.DepEmpManagmentSystem.assignment.DTOs.EmployeeResponseDTO;
import com.DepEmpManagmentSystem.assignment.Services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO requestDTO) {
        return ResponseEntity.ok(employeeService.createEmployee(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDTO requestDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // Business Logic Endpoint
    @PostMapping("/adjust-salary")
    public ResponseEntity<String> adjustSalary(@RequestBody Map<String, Object> payload) {
        Long departmentId = ((Number) payload.get("departmentId")).longValue();
        int performanceScore = (Integer) payload.get("performanceScore");

        employeeService.adjustSalary(departmentId, performanceScore);
        return ResponseEntity.ok("Salary adjustment completed for department: " + departmentId);
    }
}
