package com.genshin.service.impl;

import com.genshin.service.EncodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncodeServiceImpl implements EncodeService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword,encodedPassword);
    }

    @Override
    public String encoder(String password) {
        return passwordEncoder.encode(password);
    }

}
