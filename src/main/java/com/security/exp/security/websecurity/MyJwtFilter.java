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

            UserDetails userDetails = usersService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null,  userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


        }
        filterChain.doFilter(request, response);
    }

    private String getJwtTokenFromRequest(HttpServletRequest request){
        String JwtTokenWithBearer = request.getHeader("Authorization");
        if(JwtTokenWithBearer != null && JwtTokenWithBearer.startsWith("Bearer ")){
            return JwtTokenWithBearer;
        }
        return null;
    }
    
}
