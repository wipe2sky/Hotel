package com.kurtsevich.hotel.security;

import com.kurtsevich.hotel.api.service.IUserService;
import com.kurtsevich.hotel.model.security.User;
import com.kurtsevich.hotel.security.jwt.JwtUser;
import com.kurtsevich.hotel.security.jwt.JwtUserFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUsersDetailsService implements UserDetailsService {
    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with username {} successfully loaded", username);
        return jwtUser;
    }
}
