package com.example.tydemo.service;


import com.example.tydemo.domain.Student;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
     List<Student> findAll();
     //只分页的查询
     Page<Student> findAll(Pageable pageable);
    //分页带过滤的查询
    public Page<Student> findAll(Example<Student> student, Pageable pageable);

    Student findNameAndPassword(String name, String password) throws Exception;

    Student findById(Integer id);
     List<Student> findByName(String name);

    List<Student> findByNameAndPassword(String name, String password);

    Student insert(Student student);

    Student update(Student student);

    void delete(Student student);
    //根据id删除
     void deletee(Integer id);
     void delete(Integer id) ;
}
