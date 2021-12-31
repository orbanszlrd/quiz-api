package com.orbanszlrd.quizapi.user.dto;

import com.orbanszlrd.quizapi.user.Gender;
import com.orbanszlrd.quizapi.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class UpdateUser {
    private String username;
    private String email;
    private String password;
    private Boolean enabled;
    private Role role;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Gender gender;

    public UpdateUser(String username, String email, String password, Boolean enabled, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }
}
