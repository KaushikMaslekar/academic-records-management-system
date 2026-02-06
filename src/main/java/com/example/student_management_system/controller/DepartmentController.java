package com.example.student_management_system.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_management_system.entity.Department;
import com.example.student_management_system.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    //ResponseEntity is a generic class in Spring Framework that represents an HTTP response, including headers, body, and status code.
    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        Department saved = service.add(department);
        return ResponseEntity.created(URI.create("/api/departments/" + saved.getId())).body(saved);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Department>> addDepartments(@RequestBody List<Department> departments) {
        List<Department> saved = service.addAll(departments);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // .map(ResponseEntity::ok) is a method reference that transforms the Department object returned by service.getById(id) into a ResponseEntity with an HTTP status of 200 OK.
    // .orElse(ResponseEntity.notFound().build()) provides a fallback mechanism. If the Optional returned by service.getById(id) is empty (meaning no Department was found with the given id), this part of the code will execute, returning a ResponseEntity with an HTTP status of 404 Not Found.
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartmentById(@PathVariable String id, @RequestBody Department department) {
        return service.update(id, department)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String id) {
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
