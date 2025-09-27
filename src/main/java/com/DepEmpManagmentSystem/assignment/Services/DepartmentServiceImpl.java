package com.DepEmpManagmentSystem.assignment.Services.impl;

import com.DepEmpManagmentSystem.assignment.DTOs.DepartmentDTO;
import com.DepEmpManagmentSystem.assignment.Entities.DepartmentEntity;
import com.DepEmpManagmentSystem.assignment.Repositories.DepartmentRepository;
import com.DepEmpManagmentSystem.assignment.Services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        DepartmentEntity entity = DepartmentEntity.builder()
                .name(departmentDTO.getName())
                .code(departmentDTO.getCode())
                .build();
        DepartmentEntity saved = departmentRepository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        DepartmentEntity entity = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        return mapToDTO(entity);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        DepartmentEntity entity = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        entity.setName(departmentDTO.getName());
        entity.setCode(departmentDTO.getCode());

        DepartmentEntity updated = departmentRepository.save(entity);
        return mapToDTO(updated);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private DepartmentDTO mapToDTO(DepartmentEntity entity) {
        return DepartmentDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .build();
    }
}
