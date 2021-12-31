package com.orbanszlrd.quizapi.user.dto;

import com.orbanszlrd.quizapi.user.Gender;
import com.orbanszlrd.quizapi.user.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class AddUser {
    private String username;
    private String email;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Gender gender;

    public AddUser(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
