package com.genshin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class TestController {
    @GetMapping("/status")
    public String checkStatus(){
        return "Server is running";
    }

    @GetMapping("/login")
    public String checkLogin(){
        return "Login successfully";
    }

    @GetMapping("/test")
    public String test(){
        return String.format("%s%s","test","%");
    }


}
