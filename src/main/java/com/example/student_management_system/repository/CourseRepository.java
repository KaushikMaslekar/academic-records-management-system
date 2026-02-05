package com.example.student_management_system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.student_management_system.entity.Course;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {// Inherits CRUD operations for Course entity //<Course, String> indicates Course is the entity type and String is the type of its primary key
}
