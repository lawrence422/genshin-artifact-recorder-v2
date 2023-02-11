package com.genshin.filter;

import com.genshin.dao.UserProfile;
import com.genshin.service.UserService;
import com.genshin.util.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader!=null){
            String accessToken=authHeader.replace("Bearer","");
            if (JWTUtils.validateToken(accessToken)){
                String username=JWTUtils.getUsername(accessToken);
                if (username!=null&&userService.userExist(username)){
                    UserProfile userProfile=(UserProfile) userDetailsService.loadUserByUsername(username);
                    Authentication authentication=new UsernamePasswordAuthenticationToken(userProfile,null,userProfile.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
