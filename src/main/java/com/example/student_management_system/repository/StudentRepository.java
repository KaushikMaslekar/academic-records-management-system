package com.example.student_management_system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository; // Marks this interface as a Spring Data Repository

import com.example.student_management_system.entity.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {// Inherits CRUD operations for Student entity //<Student, String> indicates Student is the entity type and String is the type of its primary key
}
