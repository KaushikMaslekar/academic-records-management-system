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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_management_system.entity.Enrollment;
import com.example.student_management_system.service.EnrollmentService;

/**
 * Enrollment Management REST Controller Handles student course enrollments,
 * grades, and enrollment tracking
 */

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    /**
     * Enroll a student in a course
     * POST /api/enrollments?studentId={studentId}&courseId={courseId}
     * @param studentId Student ID
     * @param courseId Course ID
     * @return Created enrollment or error message
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> enrollStudent(@RequestParam String studentId, @RequestParam String courseId) {
        try {
            Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
            return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enrollment failed: " + e.getMessage());
        }
    }

    /**
     * Get all enrollments for a student
     * GET /api/enrollments/student/{studentId}
     * @param studentId Student ID
     * @return List of enrollments for the student
     */
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Enrollment>> getStudentEnrollments(@PathVariable String studentId) {
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
        return ResponseEntity.ok(enrollments);
    }

    /**
     * Get all active enrollments for a student
     * GET /api/enrollments/student/{studentId}/active
     * @param studentId Student ID
     * @return List of active enrollments
     */
    @GetMapping("/student/{studentId}/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Enrollment>> getActiveEnrollments(@PathVariable String studentId) {
        List<Enrollment> enrollments = enrollmentService.getActiveEnrollments(studentId);
        return ResponseEntity.ok(enrollments);
    }

    /**
     * Get all enrollments in a course
     * GET /api/enrollments/course/{courseId}
     * @param courseId Course ID
     * @return List of enrollments in the course
     */
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Enrollment>> getCourseEnrollments(@PathVariable String courseId) {
        List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseId);
        return ResponseEntity.ok(enrollments);
    }

    /**
     * Get enrollment count for a course
     * GET /api/enrollments/course/{courseId}/count
     * @param courseId Course ID
     * @return Number of enrollments in the course
     */
    @GetMapping("/course/{courseId}/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getCourseEnrollmentCount(@PathVariable String courseId) {
        long count = enrollmentService.getCourseEnrollmentCount(courseId);
        return ResponseEntity.ok().body("{ \"enrollmentCount\": " + count + " }");
    }

    /**
     * Get specific enrollment by ID
     * GET /api/enrollments/{enrollmentId}
     * @param enrollmentId Enrollment ID
     * @return Enrollment details or 404 if not found
     */
    @GetMapping("/{enrollmentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable String enrollmentId) {
        return enrollmentService.getEnrollmentById(enrollmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update student grade and attendance
     * PATCH /api/enrollments/{enrollmentId}/grade?grade={grade}&attendance={attendance}
     * @param enrollmentId Enrollment ID
     * @param grade Numeric grade (0-100)
     * @param attendance Attendance percentage (0-100)
     * @return Updated enrollment
     */
    @PatchMapping("/{enrollmentId}/grade")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateGrade(
            @PathVariable String enrollmentId,
            @RequestParam Double grade,
            @RequestParam(required = false, defaultValue = "0") Double attendance) {
        try {
            Enrollment updated = enrollmentService.updateGrade(enrollmentId, grade, attendance)
                    .orElse(null);
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Grade update failed: " + e.getMessage());
        }
    }

    /**
     * Update enrollment status
     * PATCH /api/enrollments/{enrollmentId}/status?status={status}
     * Status values: ACTIVE, COMPLETED, WITHDRAWN
     * @param enrollmentId Enrollment ID
     * @param status New status
     * @return Updated enrollment
     */
    @PatchMapping("/{enrollmentId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStatus(
            @PathVariable String enrollmentId,
            @RequestParam String status) {
        try {
            Enrollment updated = enrollmentService.updateStatus(enrollmentId, status)
                    .orElse(null);
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status update failed: " + e.getMessage());
        }
    }

    /**
     * Withdraw student from a course
     * PATCH /api/enrollments/{enrollmentId}/withdraw
     * @param enrollmentId Enrollment ID
     * @return Updated enrollment with WITHDRAWN status
     */
    @PatchMapping("/{enrollmentId}/withdraw")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Enrollment> withdrawFromCourse(@PathVariable String enrollmentId) {
        return enrollmentService.withdrawFromCourse(enrollmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get student GPA across all completed courses
     * GET /api/enrollments/student/{studentId}/gpa
     * @param studentId Student ID
     * @return Student's cumulative GPA
     */
    @GetMapping("/student/{studentId}/gpa")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getStudentGPA(@PathVariable String studentId) {
        Double gpa = enrollmentService.calculateStudentGPA(studentId);
        return ResponseEntity.ok().body("{ \"studentId\": \"" + studentId + "\", \"gpa\": " + gpa + " }");
    }

    /**
     * Get active enrollment count for a student
     * GET /api/enrollments/student/{studentId}/count
     * @param studentId Student ID
     * @return Number of active enrollments
     */
    @GetMapping("/student/{studentId}/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getActiveEnrollmentCount(@PathVariable String studentId) {
        long count = enrollmentService.getActiveEnrollmentCount(studentId);
        return ResponseEntity.ok().body("{ \"activeCount\": " + count + " }");
    }

    /**
     * Delete enrollment (remove student from course)
     * DELETE /api/enrollments/{enrollmentId}
     * @param enrollmentId Enrollment ID
     * @return 204 No Content on success
     */
    @DeleteMapping("/{enrollmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable String enrollmentId) {
        if (enrollmentService.deleteEnrollment(enrollmentId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get all enrollments with specific status
     * GET /api/enrollments/status/{status}
     * Status values: ACTIVE, COMPLETED, WITHDRAWN
     * @param status Enrollment status
     * @return List of enrollments with specified status
     */
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStatus(@PathVariable String status) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStatus(status);
        return ResponseEntity.ok(enrollments);
    }
}
