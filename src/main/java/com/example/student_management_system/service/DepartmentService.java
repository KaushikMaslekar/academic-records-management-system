package com.example.student_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.student_management_system.entity.Department;
import com.example.student_management_system.repository.DepartmentRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository repository; // Repository for Department entity

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public Department add(Department department) {
        return repository.save(department);
    }

    public List<Department> addAll(List<Department> departments) {
        return repository.saveAll(departments);
    }

    public List<Department> getAll() {
        return repository.findAll();
    }

    public Optional<Department> getById(String id) {
        return repository.findById(id);
    }

    public Optional<Department> update(String id, Department updated) {
        return repository.findById(id).map(existing -> {
            existing.setDepartmentName(updated.getDepartmentName());
            existing.setDepartmentCode(updated.getDepartmentCode());
            existing.setHeadOfDepartment(updated.getHeadOfDepartment());
            existing.setEmail(updated.getEmail());
            existing.setPhone(updated.getPhone());
            existing.setLocation(updated.getLocation());
            existing.setNumberOfStaff(updated.getNumberOfStaff());
            return repository.save(existing);
        });
    }

    public boolean delete(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
