package com.example.tydemo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class HttpAspect {
        @Pointcut("execution(* com.example.tydemo.webapi.StudentRestController.*(.. ))")
        public void log() {
        }
        @Before("log()")
        public void doBefore() {
                System.out.println("准备开始执行list...");
        }

        @After("log()")
        public  void doAfter() {
                System.out.println("执行list完，准备返回...");
        }

        @AfterReturning(returning = "object", pointcut = "log()")
        public void afterR(Object object) {
                log.info("response={}", object);
        }
}
