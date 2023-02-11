package com.genshin.service;

import com.genshin.entity.User;

public interface UserService {
    User findUserByName(String username);

    User save(User user);

    boolean userExist(String username);
}
