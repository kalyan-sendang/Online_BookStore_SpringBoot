package com.task.lms.controller;

import com.task.lms.config.JwtService;
import com.task.lms.model.User;
import com.task.lms.service.AuthRequest;
import com.task.lms.service.UserService;
import com.task.lms.utils.CustomException;
import com.task.lms.utils.ResponseWrapper;
import com.task.lms.utils.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/user")
    private ResponseEntity<ResponseWrapper> getAllUser() {
        List<UserDTO> users = userService.getAllUser();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Users retrieved successfully");
        response.setResponse(users);
        return ResponseEntity.ok(response);
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
        if (updatedUserDTO.getId() != null) {
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

    @DeleteMapping("/user/{id}")
    private ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("id") int id) {
            userService.deleteUser(id);
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User deleted successfully");
            return ResponseEntity.ok(response);
    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        System.out.println("Not working");
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getUserName());
                return ResponseEntity.ok(token);
            } else {
                throw new CustomException("Invalid credentials");
            }
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }


}

