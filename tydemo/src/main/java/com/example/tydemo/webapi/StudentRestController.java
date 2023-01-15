package com.example.tydemo.webapi;

import com.example.tydemo.core.PageUtils;
import com.example.tydemo.domain.Student;
import com.example.tydemo.service.StudentService;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/webapi/student")
public class StudentRestController {
    @Autowired
    private StudentService studentService;

    /*
        url:/webapi/student/list
    * */
    @GetMapping("/list")
    public List<Student> getAll() {
        List<Student> students = studentService.findAll();
        return students;
    }
    //分页，读取全部内容
    @GetMapping("/getbypage")
    public PageUtils getByPage(@RequestParam(value = "page",defaultValue = "0") Integer page,
                               @RequestParam(value = "size",defaultValue = "4") Integer size,
                               @RequestParam(value = "name",defaultValue = "")String name) {
        Page<Student> studentPage;
        if(ObjectUtils.isEmpty(name)){
            Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.ASC,"id"));
            studentPage = studentService.findAll(pageable);
        } else {
            Student student = new Student();
            student.setName(name);
            Pageable pageable = PageRequest.of(0,10,Sort.by(Sort.Direction.ASC,"id"));
            ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains());
            Example<Student> example = Example.of(student, matcher);
            studentPage = studentService.findAll(example,pageable);
        }
        PageUtils pageUtils = new PageUtils(studentPage.getContent(),studentPage.getTotalElements());
        return pageUtils;
    }

    /*
        url:/webapi/student/get/id
    * */
    @GetMapping("/get/{id}")
    public Student get(@PathVariable Integer id) {
        Student student = studentService.findById(id);
        student.setPassword("");
        return student;
    }


//    /webapi/student/insert
    @PostMapping("/insert")
    public Student insert(Student student) {

        Student student1 = studentService.insert(student);
        return student1;
    }

    @PutMapping("/update")
    public Student update(Student student) {
        //读取旧的数据
        Student oldstudent = studentService.findById(student.getId());
        if(ObjectUtils.isEmpty(student.getPassword())){
            //没有新密码就用旧密码
            student.setPassword(oldstudent.getPassword());
        }

        student = studentService.update(student);
        return student;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {

        studentService.delete(id);
    }


}
