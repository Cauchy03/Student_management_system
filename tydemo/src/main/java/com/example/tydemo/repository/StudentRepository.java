package com.example.tydemo.repository;

import com.example.tydemo.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    List<Student> findByNameLike(String name);

    List<Student> findByNameAndPassword(String name,String password);
//
//    List<Student> findNameAndPassword(String name, String password);
}
