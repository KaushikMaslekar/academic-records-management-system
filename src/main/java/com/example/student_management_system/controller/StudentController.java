package com.example.student_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_management_system.entity.Student;
import com.example.student_management_system.service.StudentService;

/**
 * Student Management REST Controller Provides endpoints for CRUD operations on
 * student records
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Get all students GET /api/students
     *
     * @return List of all students
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * Get student by ID GET /api/students/{id}
     *
     * @param id Student ID
     * @return Student details or 404 if not found
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        return studentService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create new student POST /api/students
     *
     * @param student Student object to create
     * @return Created student with generated ID
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student savedStudent = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    /**
     * Full update - replaces all student fields PUT /api/students/{id} All
     * fields must be provided in the request body
     *
     * @param id Student ID to update
     * @param student Complete student object with all fields
     * @return Updated student or 404 if not found, 400 if validation fails
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStudent(@PathVariable String id, @RequestBody Student student) {
        // Validate that student object has minimum required fields
        if (student.getName() == null || student.getName().isEmpty()
                || student.getEmail() == null || student.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Student name and email are required for update");
        }

        return studentService.update(id, student)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Partial update - updates only provided fields PATCH /api/students/{id}
     * Fields that are null in the request body will not be updated
     *
     * @param id Student ID to update
     * @param student Student object with fields to update (others can be null)
     * @return Updated student or 404 if not found
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Student> partialUpdateStudent(@PathVariable String id, @RequestBody Student student) {
        return studentService.partialUpdate(id, student)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete student DELETE /api/students/{id}
     *
     * @param id Student ID to delete
     * @return 204 No Content on success
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        // Check if student exists before deletion
        if (studentService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
