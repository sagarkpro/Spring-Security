package com.security.exp.security.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.security.exp.security.dtos.RegisterDto;

import jakarta.validation.Valid;

public interface UsersService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    public ResponseEntity<?> registerUser(RegisterDto dto);
}
