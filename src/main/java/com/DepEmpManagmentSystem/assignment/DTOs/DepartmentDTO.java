package com.DepEmpManagmentSystem.assignment.DTOs;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO {
    private Long id;
    private String name;
    private String code;
}
