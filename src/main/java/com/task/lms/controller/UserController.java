package com.task.lms.controller;

import com.task.lms.dto.UserDTO;
import com.task.lms.model.User;
import com.task.lms.model.UserProfile;
import com.task.lms.service.UserService;
import com.task.lms.utils.CustomException;
import com.task.lms.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/api")
@Tag(name = "User Controller", description = "This is user api for user")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseWrapper> insertUser(@Valid @RequestBody User user) throws CustomException {
        try {
            UserDTO createdUserDTO = userService.insertUser(user);
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setMessage("User created successfully");
            response.setSuccess(true);
            response.setResponse(createdUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseWrapper> getUser(@PathVariable("id") int id) {
        UserDTO userDTO = userService.getUserById(id);
        ResponseWrapper response = new ResponseWrapper();
        if (userDTO != null) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User retrieved successfully");
            response.setSuccess(true);
            response.setResponse(userDTO);
            return ResponseEntity.ok(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

/*
    @PutMapping("/user/{id}")
    private ResponseEntity<ResponseWrapper> updateUser( @PathVariable("id") int id,@Valid @RequestBody User user) {
        UserDTO updatedUserDTO = userService.update(id, user);
        ResponseWrapper response = new ResponseWrapper();
        if (updatedUserDTO.getUserId() != null) {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User updated successfully");
            response.setSuccess(true);
            response.setResponse(updatedUserDTO);
            return ResponseEntity.ok(response);
        } else {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
*/


    @GetMapping("/userprofile")
    public ResponseEntity<UserProfile> userProfile(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        String userName = (String) request.getAttribute("userName");
        String email = (String) request.getAttribute("email");
        String role = (String) request.getAttribute("role");
        UserProfile userProfile = new UserProfile(userId, userName, email, role);
        return ResponseEntity.ok(userProfile);
    }


}

