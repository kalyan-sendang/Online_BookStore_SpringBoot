package com.task.lms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "test_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "Invalid Username")
    private String userName;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

/*    static enum ROLE {
        USER,
        INTERN,
        ADMIN
    }*/

    @NotBlank(message = "Role is mandatory")
    @Pattern(regexp = "^(USER|INTERN|ADMIN)$" ,message = "Invalid Roles")
    private String role;

}
