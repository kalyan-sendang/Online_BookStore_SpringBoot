package com.task.lms.service;

import com.task.lms.model.User;
import com.task.lms.repository.UserRepository;
import com.task.lms.utils.CustomException;
import com.task.lms.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService{

    @Autowired

    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    //insert student
    public UserDTO insertUser(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return new UserDTO(user.getUserId(), user.getUserName(), user.getEmail());
    }

    //get all students
    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll(Sort.by("userId"));
        return users.stream()
                .map(user -> new UserDTO(user.getUserId(),user.getUserName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    //get single student
    public UserDTO getUserById(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UserDTO(user.getUserId(),user.getUserName(), user.getEmail());
        } else {
            return null;
        }
    }
//for thymeleaf get a single user
    public User getAUserById(int id){
        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isPresent()){
            User idUser = optUser.get();
            return idUser;
        }else{
            return null;
        }
    }
//Get user by userName
    public User getUserByUsername(String userName){
        Optional<User> optionalUser = userRepository.findUserByUserName(userName);
        if(optionalUser.isPresent()){
            User nameUser = optionalUser.get();
            return nameUser;
        }else{
            return null;
        }
    }
    //update user
    public UserDTO update(int id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update properties of the existing user with values from the updated user
            existingUser.setUserId(id);
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setEmail(updatedUser.getEmail());
            User user =  userRepository.save(existingUser);
            return new UserDTO(user.getUserId(), user.getUserName(),user.getEmail());
        }else{
            return null;
        }
    }


    //delete student
    public void deleteUser(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
        }else{
            throw new CustomException("User not found with ID: " + id);
        }
    }
}