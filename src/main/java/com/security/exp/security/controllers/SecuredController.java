package com.security.exp.security.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/secure")
public class SecuredController {
    @GetMapping("/pass")
    public String pass() {
        return "THIS MEHTOD REQUIRES PASS";
    }
    
}
