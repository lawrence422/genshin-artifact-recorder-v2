package com.genshin.service.impl;

import com.genshin.dao.UserProfile;
import com.genshin.exception.MissingAuthorityException;
import com.genshin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserdetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = userService.findUserByName(username);
        if (user != null) {
            if (user.getAuthority() != null) {
                return new UserProfile(username, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthority()));
            } else {
                throw new MissingAuthorityException();
            }
        }
        throw new UsernameNotFoundException("username not found.");
    }
}
