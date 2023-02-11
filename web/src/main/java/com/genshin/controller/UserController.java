package com.genshin.controller;

import com.genshin.entity.User;
import com.genshin.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private RegisterService userService;
    @PostMapping("/register")
    public User register(@RequestBody @Validated User user){
        return userService.register(user);
    }
}
