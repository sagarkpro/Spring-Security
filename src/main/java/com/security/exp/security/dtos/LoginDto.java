package com.security.exp.security.dtos;

import com.security.exp.security.entities.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6)
    private String password;
}
