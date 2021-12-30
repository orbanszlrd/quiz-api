package com.orbanszlrd.quizapi.user.dto;

import com.orbanszlrd.quizapi.user.Gender;
import com.orbanszlrd.quizapi.user.Role;
import lombok.Data;

import java.sql.Date;

@Data
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
}
