package com.example.tydemo.webapi;

import com.example.tydemo.core.RUtil;
import com.example.tydemo.domain.Student;
import com.example.tydemo.service.StudentService;
import com.example.tydemo.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginRestController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/student")
    public R student(String name,String password) throws Exception{
        Student student = studentService.findNameAndPassword(name,password);
        return RUtil.success(student);
    }
}
