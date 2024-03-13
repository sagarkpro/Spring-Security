package com.security.exp.security.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.exp.security.daos.UsersDao;
import com.security.exp.security.dtos.LoginDto;
import com.security.exp.security.dtos.RegisterDto;
import com.security.exp.security.services.UsersService;
import com.security.exp.security.websecurity.MyJwtService;

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
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    MyJwtService myJwtService;


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

        // Authentication.authenticate will automatically authenticate the user using UsernamePasswordAuthenticationToken's object
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        // we are storing authentication in SecurityContectHolder object so that user don't have to login for every request
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("\n\n" + SecurityContextHolder.getContext() + "\n\n");
        String token = myJwtService.JwtTokenGenerator(authentication);

        return ResponseEntity.ok().body(token);
    }


    @GetMapping("/nopass")
    public String nopass() {
        return "no pass needed!";
    }
    
    
    
}
