package com.security.exp.security.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.exp.security.entities.Users;



public interface UsersDao extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
