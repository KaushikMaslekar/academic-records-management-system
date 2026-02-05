package com.example.student_management_system.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
public class Course {

    @Id
    private String id;

    private String CourseCode;
    private String CourseName;
    private String Description;
    private int Credits;
    private String Department;
    private String Semester;
    private int MaxEnrollment;

    public Course() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        this.CourseCode = courseCode;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        this.CourseName = courseName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public int getCredits() {
        return Credits;
    }

    public void setCredits(int credits) {
        this.Credits = credits;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        this.Department = department;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        this.Semester = semester;
    }

    public int getMaxEnrollment() {
        return MaxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.MaxEnrollment = maxEnrollment;
    }

}
