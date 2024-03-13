package com.security.exp.security.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.exp.security.daos.UsersDao;
import com.security.exp.security.dtos.LoginDto;
import com.security.exp.security.dtos.RegisterDto;
import com.security.exp.security.services.UsersService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UsersService usersService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto dto) {
        return usersService.registerUser(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().body("Logged in succesfully");
    }
    
    
}
