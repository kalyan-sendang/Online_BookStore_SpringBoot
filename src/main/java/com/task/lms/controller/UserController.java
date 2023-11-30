package com.task.lms.controller;

import com.task.lms.config.JwtService;
import com.task.lms.model.User;
import com.task.lms.model.UserProfile;
import com.task.lms.service.AuthRequest;
import com.task.lms.service.TokenResponse;
import com.task.lms.service.UserService;
import com.task.lms.utils.CustomException;
import com.task.lms.utils.ResponseWrapper;
import com.task.lms.utils.UserDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/user")
    private ResponseEntity<ResponseWrapper> insertUser(@Valid @RequestBody User user) throws CustomException {
        try {
            UserDTO createdUserDTO = userService.insertUser(user);
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setMessage("User created successfully");
            response.setResponse(createdUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }


    @GetMapping("/user/{id}")
    private ResponseEntity<ResponseWrapper> getUser(@PathVariable("id") int id) {
        UserDTO userDTO = userService.getUserById(id);
        ResponseWrapper response = new ResponseWrapper();
        if (userDTO != null) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User retrieved successfully");
            response.setResponse(userDTO);
            return ResponseEntity.ok(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    @PutMapping("/user/{id}")
    private ResponseEntity<ResponseWrapper> updateUser( @PathVariable("id") int id,@Valid @RequestBody User user) {
        UserDTO updatedUserDTO = userService.update(id, user);
        ResponseWrapper response = new ResponseWrapper();
        if (updatedUserDTO.getUserId() != null) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User updated successfully");
            response.setResponse(updatedUserDTO);
            return ResponseEntity.ok(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<TokenResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        System.out.println("Not working");
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

    @GetMapping("/userprofile")
    public ResponseEntity<UserProfile> userProfile(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        String userName = (String) request.getAttribute("userName");
        String email = (String) request.getAttribute("email");
        String role = (String) request.getAttribute("role");
            UserProfile userProfile = new UserProfile(userId, userName, email, role);
            return ResponseEntity.ok(userProfile);
    }


}

