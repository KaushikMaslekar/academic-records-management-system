package com.example.student_management_system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.student_management_system.entity.Department;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {
    // Inherits CRUD operations for Department entity
    // <Department, String> indicates Department is the entity type and String is the type of its primary key
}
