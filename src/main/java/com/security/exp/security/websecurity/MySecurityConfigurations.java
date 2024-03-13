package com.security.exp.security.websecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MySecurityConfigurations {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable()) // this is a new method to disable csrf
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // again replacement for deprecated methods, we are creating stateless session as this is a REST Application 
            .authorizeHttpRequests((authorize)->authorize //again, old method is deprecated so this is the latest API
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/swagger-ui/**").permitAll() // ant matchers is also deprecated, so this is the replacement
            .requestMatchers( "/common/**").permitAll() // we can also provide allowed methods before the url pattern
            .anyRequest().authenticated()) // rest all the end points are authenticated
            .httpBasic(Customizer.withDefaults()) // httpBasic is a type of security, old method is deprecated
            
            // this will add our custom filter to intercept any request before it reaches controller
            .addFilterBefore(myJwtFilter(), UsernamePasswordAuthenticationFilter.class);

        
        return http.build(); // .build() returns the SecurityFilterChain object using the above config stored in HttpSecurity object
    }


    @Bean // we are providing AuthenricationManager object for the @Autowired
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean // providing PasswordEncoder's object (BCrypPasswordEncoder) for @Autowired
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyJwtFilter myJwtFilter(){
        return new MyJwtFilter();
    }
}
