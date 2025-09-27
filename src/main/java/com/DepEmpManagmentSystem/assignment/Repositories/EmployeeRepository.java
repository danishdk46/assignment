package com.DepEmpManagmentSystem.assignment.Repositories;

import com.DepEmpManagmentSystem.assignment.Entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findByDepartmentId(Long departmentId);
}
