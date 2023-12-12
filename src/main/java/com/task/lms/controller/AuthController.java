package com.task.lms.controller;

import com.task.lms.model.AuthRequest;
import com.task.lms.model.TokenResponse;
import com.task.lms.model.User;
import com.task.lms.service.JwtService;
import com.task.lms.service.UserService;
import com.task.lms.utils.CustomException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }



    @PostMapping("/user/login")
    public ResponseEntity<TokenResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {

            // Attempt to authenticate the user using the provided credentials
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

            // If the authentication is successful, generate a JWT token
            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                User user = userService.getUserByUsername(userDetails.getUsername());
                String token = jwtService.generateToken(user);
                final Cookie cookie = new Cookie("auth", token);
                cookie.setSecure(false);
                cookie.setHttpOnly(true);
                cookie.setMaxAge(50400);
                cookie.setPath("/api");
                response.addCookie(cookie);

                return ResponseEntity.ok().body(new TokenResponse(token));
            } else {
                throw new CustomException("Invalid credentials");
            }
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
