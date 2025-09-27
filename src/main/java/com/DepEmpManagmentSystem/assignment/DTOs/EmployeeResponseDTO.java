package com.DepEmpManagmentSystem.assignment.DTOs;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private String email;
    private BigDecimal salary;
    private LocalDate joiningDate;
    private String departmentName;
}
