package com.example.student_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.student_management_system.entity.Course;
import com.example.student_management_system.entity.Enrollment;
import com.example.student_management_system.entity.Student;
import com.example.student_management_system.repository.CourseRepository;
import com.example.student_management_system.repository.EnrollmentRepository;
import com.example.student_management_system.repository.StudentRepository;

/**
 * Enrollment Service Handles business logic for student course enrollments
 */
@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Enroll a student in a course Validates that both student and course
     * exist, and prevents duplicate enrollments
     */
    public Enrollment enrollStudent(String studentId, String courseId) throws Exception {
        // Validate student exists
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new Exception("Student not found with ID: " + studentId));

        // Validate course exists
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new Exception("Course not found with ID: " + courseId));

        // Check if student is already enrolled in this course
        Optional<Enrollment> existing = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);
        if (existing.isPresent()) {
            throw new Exception("Student is already enrolled in this course");
        }

        // Check if course has available slots
        long enrollmentCount = enrollmentRepository.countByCourseId(courseId);
        if (enrollmentCount >= course.getMaxEnrollment()) {
            throw new Exception("Course is at maximum enrollment capacity");
        }

        // Create new enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        enrollment.setStudentName(student.getName());
        enrollment.setCourseName(course.getCourseName());
        enrollment.setCourseCode(course.getCourseCode());
        enrollment.setStatus("ACTIVE");
        enrollment.setEnrollmentDate(java.time.LocalDate.now());

        return enrollmentRepository.save(enrollment);
    }

    /**
     * Get all enrollments for a specific student
     */
    public List<Enrollment> getStudentEnrollments(String studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    /**
     * Get all active enrollments for a specific student
     */
    public List<Enrollment> getActiveEnrollments(String studentId) {
        return enrollmentRepository.findByStudentIdAndStatus(studentId, "ACTIVE");
    }

    /**
     * Get all enrollments in a specific course
     */
    public List<Enrollment> getCourseEnrollments(String courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    /**
     * Get specific enrollment by ID
     */
    public Optional<Enrollment> getEnrollmentById(String enrollmentId) {
        return enrollmentRepository.findById(enrollmentId);
    }

    /**
     * Update enrollment grade and calculate GPA
     */
    public Optional<Enrollment> updateGrade(String enrollmentId, Double grade, Double attendance) throws Exception {
        if (grade < 0 || grade > 100) {
            throw new Exception("Grade must be between 0 and 100");
        }
        if (attendance < 0 || attendance > 100) {
            throw new Exception("Attendance must be between 0 and 100");
        }

        return enrollmentRepository.findById(enrollmentId).map(enrollment -> {
            enrollment.setGrade(grade);
            enrollment.setAttendancePercentage(attendance);

            // Calculate GPA (simple conversion: 90-100 -> 4.0, 80-89 -> 3.0, etc.)
            Double gpa = (grade / 25.0) - 0.4;
            enrollment.setGpa(Math.max(0, Math.min(4.0, gpa)));

            return enrollmentRepository.save(enrollment);
        });
    }

    /**
     * Update enrollment status (ACTIVE, COMPLETED, WITHDRAWN)
     */
    public Optional<Enrollment> updateStatus(String enrollmentId, String status) throws Exception {
        if (!isValidStatus(status)) {
            throw new Exception("Invalid status. Must be ACTIVE, COMPLETED, or WITHDRAWN");
        }

        return enrollmentRepository.findById(enrollmentId).map(enrollment -> {
            enrollment.setStatus(status);
            return enrollmentRepository.save(enrollment);
        });
    }

    /**
     * Withdraw student from a course
     */
    public Optional<Enrollment> withdrawFromCourse(String enrollmentId) {
        try {
            return updateStatus(enrollmentId, "WITHDRAWN");
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Get enrollment count for a course
     */
    public long getCourseEnrollmentCount(String courseId) {
        return enrollmentRepository.countByCourseId(courseId);
    }

    /**
     * Get total active enrollments for a student
     */
    public long getActiveEnrollmentCount(String studentId) {
        return enrollmentRepository.countByStudentIdAndStatus(studentId, "ACTIVE");
    }

    /**
     * Calculate student GPA across all completed courses
     */
    public Double calculateStudentGPA(String studentId) {
        List<Enrollment> completedEnrollments = enrollmentRepository.findByStudentIdAndStatus(studentId, "COMPLETED");

        if (completedEnrollments.isEmpty()) {
            return 0.0;
        }

        double totalGPA = 0;
        for (Enrollment enrollment : completedEnrollments) {
            if (enrollment.getGpa() != null) {
                totalGPA += enrollment.getGpa();
            }
        }

        return totalGPA / completedEnrollments.size();
    }

    /**
     * Delete enrollment (unenroll student from course)
     */
    public boolean deleteEnrollment(String enrollmentId) {
        if (enrollmentRepository.existsById(enrollmentId)) {
            enrollmentRepository.deleteById(enrollmentId);
            return true;
        }
        return false;
    }

    /**
     * Get all enrollments with status
     */
    public List<Enrollment> getEnrollmentsByStatus(String status) {
        return enrollmentRepository.findByStatus(status);
    }

    /**
     * Validate status is one of: ACTIVE, COMPLETED, WITHDRAWN
     */
    private boolean isValidStatus(String status) {
        return status != null && (status.equals("ACTIVE") || status.equals("COMPLETED") || status.equals("WITHDRAWN"));
    }
}
