package com.task.lms.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers( "/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/user").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/user/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.POST, "api/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "api/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/user/**").hasRole("ADMIN")

        );
        //use basic http basic authentication
        http.httpBasic(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults());

        //disable cross site resource forgery(CSRF)
       /* http.csrf(csrf -> csrf.disable());*/
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}
