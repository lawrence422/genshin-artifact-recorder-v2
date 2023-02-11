package com.genshin.service.impl;

import com.genshin.entity.User;
import com.genshin.repository.UserRepository;
import com.genshin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User findUserByName(String username) {
        final var userOptional = userRepository.findById(username);
        return userOptional.orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean userExist(String username) {
        return userRepository.existsById(username);
    }
}
