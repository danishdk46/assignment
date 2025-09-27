package com.DepEmpManagmentSystem.assignment.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private BigDecimal salary;

    @Column(nullable = false)
    private LocalDate joiningDate;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;
}
