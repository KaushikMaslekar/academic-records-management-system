package com.example.student_management_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.student_management_system.entity.Enrollment;

/**
 * Enrollment Repository Provides database operations for Enrollment entities
 */
@Repository
public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {

    /**
     * Find all enrollments for a specific student
     */
    List<Enrollment> findByStudentId(String studentId);

    /**
     * Find all enrollments for a specific course
     */
    List<Enrollment> findByCourseId(String courseId);

    /**
     * Find specific enrollment by student and course IDs (unique combination)
     */
    Optional<Enrollment> findByStudentIdAndCourseId(String studentId, String courseId);

    /**
     * Find all active enrollments for a student
     */
    List<Enrollment> findByStudentIdAndStatus(String studentId, String status);

    /**
     * Find all enrollments with specific status
     */
    List<Enrollment> findByStatus(String status);

    /**
     * Count total enrollments in a course
     */
    long countByCourseId(String courseId);

    /**
     * Count active enrollments for a student
     */
    long countByStudentIdAndStatus(String studentId, String status);

    /**
     * Custom query to find enrollments with grade above threshold
     */
    @Query("{ 'courseId': ?0, 'status': 'COMPLETED', 'grade': { $gte: ?1 } }")
    List<Enrollment> findCompletedEnrollmentsWithGradeAbove(String courseId, Double gradeThreshold);

    /**
     * Find all completed enrollments for a student
     */
    List<Enrollment> findByStudentIdAndStatusNot(String studentId, String status);
}
