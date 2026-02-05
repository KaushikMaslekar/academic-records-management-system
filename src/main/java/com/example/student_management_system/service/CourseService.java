package com.example.student_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.student_management_system.entity.Course;
import com.example.student_management_system.repository.CourseRepository;

@Service
public class CourseService {

    public final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public Course add(Course course) {
        return repository.save(course);
    }

    public List<Course> getAll() {
        return repository.findAll(); // Fetches all Course entities from the database
    }

    public Optional<Course> getById(String id) {
        return repository.findById(id);
    }

    public Optional<Course> update(String id, Course updated) {
        return repository.findById(id).map(existing -> {
            existing.setCourseCode(updated.getCourseCode());
            existing.setCourseName(updated.getCourseName());
            existing.setDescription(updated.getDescription());
            existing.setCredits(updated.getCredits());
            existing.setDepartment(updated.getDepartment());
            existing.setSemester(updated.getSemester());
            existing.setMaxEnrollment(updated.getMaxEnrollment());

            return repository.save(existing);
        });
    }

    public boolean delete(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
