package com.security.exp.security.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/common")
public class CommonController {
    @GetMapping("/nopass")
    public String nopass() {
        return "THIS REQ DOES NOT REQUIRE LOGIN";
    }
    
}
