package com.kurtsevich.hotel.controller;

import com.kurtsevich.hotel.api.service.IUserService;
import com.kurtsevich.hotel.dto.AuthenticationRequestDto;
import com.kurtsevich.hotel.dto.UserRoleDto;
import com.kurtsevich.hotel.dto.UserTokenDto;
import com.kurtsevich.hotel.model.security.User;
import com.kurtsevich.hotel.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        String username = requestDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        User user = userService.findByUsername(username);
         if (user == null){
             throw new UsernameNotFoundException("User with username: " + username + "not found.");
         }
         String token = jwtTokenProvider.createToken(username, user.getRoles());

        return ResponseEntity.ok(new UserTokenDto().setUsername(username).setToken(token));
    }
}
