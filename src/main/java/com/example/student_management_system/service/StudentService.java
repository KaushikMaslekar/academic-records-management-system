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

    public Student addStudent(Student student) {
        return repository.save(student);
    }

    public List<Student> addAll(List<Student> students) {
        return repository.saveAll(students);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Optional<Student> getById(String id) {
        return repository.findById(id);
    }

    /**
     * Full update - replaces all fields of an existing student
     *
     * @param id Student ID
     * @param updated Updated student object with all fields
     * @return Optional containing updated student or empty if not found
     */
    public Optional<Student> update(String id, Student updated) {
        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setEmail(updated.getEmail());
            existing.setPhone(updated.getPhone());
            existing.setCourse(updated.getCourse());
            existing.setDepartment(updated.getDepartment());
            existing.setTeacher(updated.getTeacher());
            existing.setEnrollmentDate(updated.getEnrollmentDate());
            return repository.save(existing);
        });
    }

    /**
     * Partial update - only updates fields that are non-null in the provided
     * object
     *
     * @param id Student ID
     * @param updated Student object with only the fields to update (others can
     * be null)
     * @return Optional containing updated student or empty if not found
     */
    public Optional<Student> partialUpdate(String id, Student updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getName() != null) {
                existing.setName(updated.getName());
            }
            if (updated.getEmail() != null) {
                existing.setEmail(updated.getEmail());
            }
            if (updated.getPhone() != null) {
                existing.setPhone(updated.getPhone());
            }
            if (updated.getCourse() != null) {
                existing.setCourse(updated.getCourse());
            }
            if (updated.getDepartment() != null) {
                existing.setDepartment(updated.getDepartment());
            }
            if (updated.getTeacher() != null) {
                existing.setTeacher(updated.getTeacher());
            }
            if (updated.getEnrollmentDate() != null) {
                existing.setEnrollmentDate(updated.getEnrollmentDate());
            }
            return repository.save(existing);
        });
    }

    public void deleteStudent(String id) {
        repository.deleteById(id);
    }
}
