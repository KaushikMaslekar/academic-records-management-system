package com.example.student_management_system.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Enrollment Entity Represents the relationship between a Student and a Course
 * Tracks enrollment status, date, and grades
 */

@Document(collection = "enrollments")
@CompoundIndexes({
    @CompoundIndex(name = "student_course_idx", def = "{'studentId': 1, 'courseId': 1}", unique = true)
})
public class Enrollment {

    @Id
    private String id;

    private String studentId;       // Reference to Student ID
    private String courseId;        // Reference to Course ID
    private String studentName;     // Cached student name for convenience
    private String courseName;      // Cached course name for convenience
    private String courseCode;      // Cached course code for convenience

    private LocalDate enrollmentDate;   // When student enrolled in course
    private String status;              // ACTIVE, COMPLETED, WITHDRAWN
    private Double grade;               // Final grade (0-100)
    private String letterGrade;         // A, B, C, D, F
    private Double gpa;                 // GPA points (0-4.0)
    private Double attendancePercentage; // Attendance %

    public Enrollment() {
        this.status = "ACTIVE";
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
        updateLetterGrade(grade);
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public Double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(Double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    /**
     * Convert numeric grade to letter grade
     * A: 90-100, B: 80-89, C: 70-79, D: 60-69, F: 0-59
     */
    private void updateLetterGrade(Double grade) {
        if (grade == null) {
            return;
        }
        if (grade >= 90) {
            this.letterGrade = "A";
        } else if (grade >= 80) {
            this.letterGrade = "B";
        } else if (grade >= 70) {
            this.letterGrade = "C";
        } else if (grade >= 60) {
            this.letterGrade = "D";
        } else {
            this.letterGrade = "F";
        }
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", status='" + status + '\'' +
                ", grade=" + grade +
                ", letterGrade='" + letterGrade + '\'' +
                '}';
    }
}
