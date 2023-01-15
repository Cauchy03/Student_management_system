package com.example.tydemo.service;

import com.example.tydemo.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    void findAll() {
        List<Student> students = studentService.findAll();
        assertNotNull(students);
    }

    @Test
    void findById() {
        Student student = studentService.findById(1);
        assertNotNull(student);
    }

    @Test
    void findByNameLike() {
        List<Student> students = studentService.findByName("李%");
        assertNotNull(students);
    }

    @Test
    void findByNameAndPassword() {
        List<Student> students = studentService.findByNameAndPassword("李四","111");
        assertNotNull(students);
    }

    @Test
    void insert() {
        Student student = new Student();
        student.setName("赵六");
        student.setPassword("7777");
        student.setSex(2);
        student.setAge(20);

        studentService.insert(student);

        assertNotNull(student.getId());
    }

    @Test
    void update() {
        Student student = studentService.findById(2);
        student.setName("赵六1");

        studentService.update(student);

        assertEquals(student.getName(),"赵六1");
    }

    @Test
    void delete() {
        studentService.delete(2);
        Student student = studentService.findById(2);
        assertNull(student);
    }
}