package com.example.student_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.student_management_system.entity.Teacher;
import com.example.student_management_system.repository.TeacherRepository;

@Service
public class TeacherService {

    private final TeacherRepository repository; // Repository for Teacher entity

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public Teacher add(Teacher teacher) {
        return repository.save(teacher);
    }

    public List<Teacher> addAll(List<Teacher> teachers) {
        return repository.saveAll(teachers);
    }

    public List<Teacher> getAll() {
        return repository.findAll();
    }

    public Optional<Teacher> getById(String id) {
        return repository.findById(id);
    }

    public Optional<Teacher> update(String id, Teacher updated) {
        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setEmail(updated.getEmail());
            existing.setPhone(updated.getPhone());
            existing.setDepartment(updated.getDepartment());
            existing.setQualification(updated.getQualification());
            existing.setSpecialization(updated.getSpecialization());
            existing.setDesignation(updated.getDesignation());
            existing.setSalary(updated.getSalary());
            existing.setExperienceYear(updated.getExperienceYear());
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
