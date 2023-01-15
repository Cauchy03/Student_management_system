package com.example.tydemo.service.impl;


import com.example.tydemo.domain.Student;
import com.example.tydemo.exception.REnum;
import com.example.tydemo.exception.RException;
import com.example.tydemo.repository.StudentRepository;
import com.example.tydemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentimplService implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> findAll(){
        return studentRepository.findAll();
    }

    //只分页的查询
    @Override
    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }
    //分页带过滤的查询
    @Override
    public Page<Student> findAll(Example<Student> student, Pageable pageable) {
        return studentRepository.findAll(student,pageable);
    }

    public Student findNameAndPassword(String name, String password) throws Exception{
        List<Student> byNameAndPassword = studentRepository.findByNameAndPassword(name,password);
        if(byNameAndPassword.size()>0) {
            return byNameAndPassword.get(0);
        }else {
            throw new RException(REnum.LOGIN_ERR);
        }
    }


    public Student findById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    public  List<Student> findByName(String name){
        return studentRepository.findByNameLike(name);
    }

    public List<Student> findByNameAndPassword(String name, String password){
        return studentRepository.findByNameAndPassword(name,password);
    }

    public Student insert(Student student) {
        studentRepository.save(student);
        return student;
    }

    public Student update(Student student) {
        studentRepository.save(student);
        return student;
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }
    //根据id删除
    public void deletee(Integer id) {
        Student student = new Student();
        student.setId(id);
        studentRepository.delete(student);
    }
    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }
}
