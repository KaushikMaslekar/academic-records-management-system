package com.example.student_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.student_management_system.entity.Student;
import com.example.student_management_system.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repository; // Repository for Student entity

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student add(Student student) {
        return repository.save(student);
    }

    public List<Student> getAll() {
        return repository.findAll();
    }

    public Optional<Student> getById(String id) {
        return repository.findById(id);
    }

    public Optional<Student> update(String id, Student updated) {
        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setEmail(updated.getEmail());
            existing.setPhone(updated.getPhone());
            existing.setCourse(updated.getCourse());
            existing.setEnrollmentDate(updated.getEnrollmentDate());
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
