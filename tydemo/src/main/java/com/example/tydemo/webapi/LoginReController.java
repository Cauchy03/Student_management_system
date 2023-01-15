package com.example.tydemo.webapi;

import com.example.tydemo.domain.Student;
import com.example.tydemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/webapi/login")
public class LoginReController {
    @Autowired
    StudentService studentService;

    @GetMapping("/user")
    public int user(HttpServletRequest httpServletRequest, String username,String password){
        //判断用户名和密码是否存在
        List<Student> students = studentService.findByNameAndPassword(username,password);
        if (students.size()>0){
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("userid",students.get(0).getId());
            session.setAttribute("user",students.get(0));
            return  1; //成功
        }
        else {
            return -1;//不存在
        }
    }
}
