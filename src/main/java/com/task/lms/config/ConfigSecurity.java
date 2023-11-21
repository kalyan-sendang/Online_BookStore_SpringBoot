/*
package com.task.lms.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "api/user").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "api/user/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "api/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "api/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/user/**").hasRole("ADMIN")
        );
        //use basic http basic authentication
        http.httpBasic(Customizer.withDefaults());

        //disable cross site resource forgery(CSRF)
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")
                .roles("USER")
                .build();
        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("USER","MANAGER")
                .build();
        UserDetails ram = User.builder()
                .username("ram")
                .password("{noop}test123")
                .roles("USER","MANAGER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(john, mary, ram);
    }
}
*/
