package com.task.lms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Integer userId;
    private String Username;
    private String email;

    public UserDTO(Integer userId, String username, String email) {
        this.userId = userId;
        this.Username = username;
        this.email = email;
    }
}
