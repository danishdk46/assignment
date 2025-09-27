package com.DepEmpManagmentSystem.assignment.Repositories;

import com.DepEmpManagmentSystem.assignment.Entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    Optional<DepartmentEntity> findByCode(String code);
}
