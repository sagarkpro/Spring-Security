package com.security.exp.security.websecurity;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class MyJwtService {

    @Value("${JWT_SECRET}") // @Value is used to fetch values from appilcation.properties file
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long expirationTime;

    public String JwtTokenGenerator(Authentication authentication){
        System.out.println("\n\n" + "USER NAME : " + authentication.getName() + "\n\n");
        String jwtToken = Jwts.builder()
            .setSubject(authentication.getName())
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + expirationTime))
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)), SignatureAlgorithm.HS512)
            .compact();
        
        return jwtToken;
    }

    public String getUsername(String jwtToken){
            String []token = jwtToken.split(" ");
            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                                .build()
                                .parseClaimsJws(token[1])
                                .getBody();
            
            return claims.getSubject();
    }

    public boolean validateToken(String jwtToken){
        String []token = jwtToken.split(" ");
        try{
            System.out.println(jwtToken + "\n\n");
            
            JwtParserBuilder jwt =  Jwts.parserBuilder();
            System.out.println("\n\n" + 1 + "\n\n");
            jwt.setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)));
            System.out.println("\n\n" + 2 + "\n\n");
            JwtParser jwwwt =  jwt.build();
            System.out.println("\n\n" + 3 + "\n\n");
            jwwwt.parseClaimsJws(token[1]);
            System.out.println("\n\n" + 4 + "\n\n");
                // .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                // .build()
                // .parseClaimsJws(jwtToken);

            return true;
        }
        catch(Exception e){
            throw new AuthenticationCredentialsNotFoundException("Token is invalid");
        }

    }
}
