package com.security.exp.security.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.exp.security.entities.Users;
import java.util.List;


public interface UsersDao extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
}
