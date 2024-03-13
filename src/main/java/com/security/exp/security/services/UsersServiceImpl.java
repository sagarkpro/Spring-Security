package com.security.exp.security.services;

import java.util.Scanner;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.exp.security.daos.UsersDao;
import com.security.exp.security.dtos.RegisterDto;
import com.security.exp.security.entities.Roles;
import com.security.exp.security.entities.Users;

import jakarta.validation.Valid;


@Service
public class UsersServiceImpl implements UsersService, UserDetailsService{

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UsersDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("The email does not exist"));
    }

    @Override
    public ResponseEntity<?> registerUser(RegisterDto dto) {
        System.out.println(dto);
        if(!userDao.findByEmail(dto.getEmail()).isEmpty()) return ResponseEntity.status(HttpStatus.IM_USED).body("This email is already registered with us");

        Users user = modelMapper.map(dto, Users.class);
        user.setRole(Roles.ROLE_USER);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userDao.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User Created!");

    }
    
}
