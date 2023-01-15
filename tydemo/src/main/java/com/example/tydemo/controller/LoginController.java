package com.example.tydemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Service
@Controller
public class LoginController {
    @RequestMapping("/login/user")
    public String login(){
        return "/login/user";
    }
}
