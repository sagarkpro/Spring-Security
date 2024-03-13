package com.security.exp.security.websecurity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MyJwtService {

    @Value("${JWT_SECRET}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private String expirationTime;

    // public 

    // public String extractUsername(String jwtToken){
    //     return extractClaims(jwtToken)
    // }
}
