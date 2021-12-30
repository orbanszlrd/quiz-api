package com.orbanszlrd.quizapi.user.dto;

import com.orbanszlrd.quizapi.user.Gender;
import com.orbanszlrd.quizapi.user.Role;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class GetUser {
    private Long id;
    private String username;
    private String email;
    private boolean enabled;
    private Role role;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Gender gender;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
