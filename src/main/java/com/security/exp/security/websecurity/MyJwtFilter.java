package com.security.exp.security.websecurity;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.exp.security.services.UsersService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class MyJwtFilter extends OncePerRequestFilter {

    @Autowired
    UsersService usersService;

    @Autowired
    private MyJwtService myJwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = getJwtTokenFromRequest(request);
        if(jwtToken != null && myJwtService.validateToken(jwtToken)){
            System.out.println("\n\n" + "found jwt token: " + jwtToken + "\n\n");
            String username = myJwtService.getUsername(jwtToken);

            System.out.println("\n\n" + 11 + "\n\n");
            UserDetails userDetails = usersService.loadUserByUsername(username);

            System.out.println("\n\n" + 22 + "\n\n");
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null,  userDetails.getAuthorities());

            System.out.println("\n\n" + 33 + "\n\n");
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            
            System.out.println("\n\n" + 44 + "\n\n");
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            System.out.println("\n\n" + 55 + "\n\n");

        }
        filterChain.doFilter(request, response);
        System.out.println("\n\n" + 66 + "\n\n");
    }

    private String getJwtTokenFromRequest(HttpServletRequest request){
        String JwtTokenWithBearer = request.getHeader("Authorization");
        if(JwtTokenWithBearer != null && JwtTokenWithBearer.startsWith("Bearer ")){
            return JwtTokenWithBearer;
        }
        return null;
    }
    
}
