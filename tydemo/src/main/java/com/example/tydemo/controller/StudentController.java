package com.example.tydemo.controller;

import com.example.tydemo.domain.Student;
import com.example.tydemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@Service
public class StudentController {
    @Autowired
    StudentService studentService;

    @RequestMapping("/student/list")
    public String list(HttpServletRequest httpServletRequest, Model model) {

        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("userid")==null){
            return "redirect:/login/user";
        }

        List<Student> students = studentService.findAll();
        model.addAttribute("data",students);
        return "/student/list";
    }


}
