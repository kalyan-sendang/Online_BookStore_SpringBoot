package com.task.lms.config;


import com.task.lms.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthFilter;

    // User Creation
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(c -> c.configurationSource(corsFilter()))
                .authorizeHttpRequests(configurer ->
                                configurer
                                        .requestMatchers("/api/auth/**").permitAll()
                                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                        .requestMatchers("/api/user/**").permitAll()
                                        .requestMatchers("/api/book/**").permitAll()
                                        .requestMatchers("api/cart/**").permitAll()
                                        .requestMatchers("/api/userprofile").permitAll()
                                        .requestMatchers("api/review/**").permitAll()
                                        .requestMatchers("api/order/**").permitAll()
/*                       .requestMatchers(HttpMethod.GET, "api/user").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/user/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.POST, "api/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "api/generateToken").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/user/**").hasRole("ADMIN")*/

                );
        //use basic http basic authentication
       /* http.httpBasic(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults());*/
        http.authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        //disable cross site resource forgery(CSRF)
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean

    public CorsConfigurationSource corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
