package com.example.student_management_system.Entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.student_management_system.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


public class StudentTest {
    private Student student;

    @BeforeEach
    void setUp(){
        student = new Student();
    }

    @Test
    void testStudentDefaultConstructor(){
        assertNotNull(student);
        assertNull(student.getId());
        assertNull(student.getName());
        assertNull(student.getEmail());
        assertNull(student.getPhone());
        assertNull(student.getCourse());
        assertNull(student.getEnrollmentDate());
    }
    @Test
    void testSetandGetId(){
        String testId = "emp-123";
        //act
        student.setId(testId);
        //assert
        assertEquals(testId, student.getId());
    }

    @Test
    void testSetandGetName(){
        String testName = "Kaushik Maslekar";
        //act
        student.setName(testName);
        //assert
        assertEquals(testName, student.getName());
    }

    @Test
    void testSetandGetEmail(){
        String testEmail = "maslekarkaushik@gmail.com";
        //act
        student.setEmail(testEmail);
        //assert
        assertEquals(testEmail, student.getEmail());
    }

    @Test
    void testSetandGetPhone(){
        String testPhone = "+919325790846";
        student.setPhone(testPhone);
        //assert
        assertEquals(testPhone, student.getPhone());
    }

    @Test
    void testSetandGetCourse(){
        String testCourse = "Computer Science and Engineering";
        student.setCourse(testCourse);
        assertEquals(testCourse, student.getCourse());
    }

    @Test
    void testSetandGetEnrollmentDate(){
        LocalDate testDate = LocalDate.ofEpochDay(2026-26-1);
        student.setEnrollmentDate(testDate);
        assertEquals(testDate, student.getEnrollmentDate());

    }

    @Test
    void setAllStudentFields(){
        String id = "kaushik01";
        String name = "kaushik kishor maslekar";
        String email = "maslekarkaushik@gmail.com";
        String phone = "+919325790846";
        String course = "Computer Science and Engineering";
        LocalDate date = LocalDate.ofEpochDay(2026-26-01);

        student.setId(id);
        student.setName(name);
        student.setEmail(email);
        student.setPhone(phone);
        student.setCourse(course);
        student.setEnrollmentDate(date);

        assertEquals(id, student.getId());
        assertEquals(name, student.getName());
        assertEquals(email, student.getEmail());
        assertEquals(phone, student.getPhone());
        assertEquals(course, student.getCourse());
        assertEquals(date, student.getEnrollmentDate());
    }

    @Test
    void testNameWithEmptyString()
    {
        student.setName("");
        assertEquals("", student.getName());
    }

    @Test
    void testSetEmailWithSpecialCharacters() {
        // Arrange
        String complexEmail = "student+test.2025@universtiy.co.uk";

        // Act
        student.setEmail(complexEmail);

        // Assert
        assertEquals(complexEmail, student.getEmail());
    }
    @Test
    void testUpdateEmployeeFields() {
        // Arrange
        student.setId("emp-1");
        student.setName("Mayur Maslekar");
        student.setEmail("mayurmaslekar@gmail.com");
        student.setPhone("9325790846");

        // Act - Update fields
        student.setName("Kaushik Maslekar");
        student.setEmail("maslekarkaushik@gmail.com");
        student.setPhone("9325790846");

        // Assert
        assertEquals("emp-1", student.getId());
        assertEquals("Kaushik Maslekar", student.getName());
        assertEquals("maslekarkaushik@gmail.com", student.getEmail());
        assertEquals("9325790846", student.getPhone());
    }
    @Test
    void testSetNullValues() {
        // Arrange
        student.setId("emp-123");
        student.setName("John");

        // Act
        student.setId(null);
        student.setName(null);

        // Assert
        assertNull(student.getId());
        assertNull(student.getName());
    }

    @Test
    void testSetMultipleTimesForSameField() {
        // Arrange
        String initialEmail = "mayurmaslekar@gmail.com";
        String secondEmail = "second@example.com";
        String finalEmail = "maslekarkaushik@gmail.com";

        // Act
        student.setEmail(initialEmail);
        student.setEmail(secondEmail);
        student.setEmail(finalEmail);

        // Assert
        assertEquals(finalEmail, student.getEmail());
    }
}


