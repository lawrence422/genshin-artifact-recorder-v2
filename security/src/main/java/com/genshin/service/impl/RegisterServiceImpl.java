package com.genshin.service.impl;

import com.genshin.entity.User;
import com.genshin.service.EncodeService;
import com.genshin.service.RegisterService;
import com.genshin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    UserService userService;

    @Autowired
    EncodeService encodeService;


    @Override
    public User register(User user) {
        if (user.getAuthority() == null) {
            user.setAuthority("normal");
        }

        user.setPassword(encodeService.encoder(user.getPassword()));

        return userService.save(user);
    }



}
