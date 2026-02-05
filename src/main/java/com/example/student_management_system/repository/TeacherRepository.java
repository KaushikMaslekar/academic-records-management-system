package com.example.student_management_system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.student_management_system.entity.Teacher;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {
    // Inherits CRUD operations for Teacher entity
    // <Teacher, String> indicates Teacher is the entity type and String is the type of its primary key
}
