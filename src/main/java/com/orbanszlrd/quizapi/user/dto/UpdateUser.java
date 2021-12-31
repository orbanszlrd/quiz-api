package com.orbanszlrd.quizapi.user.dto;

import com.orbanszlrd.quizapi.user.Gender;
import com.orbanszlrd.quizapi.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@NoArgsConstructor
public class UpdateUser {
    @NotBlank
    @Size(min = 6,  max = 20)
    @Schema(description = "The username of the user", example = "dummy.user", required = true)
    private String username;

    @NotBlank
    @Email
    @Size(min = 10,  max = 50)
    @Schema(description = "The email of the user", example = "dummy@email.com", required = true)
    private String email;

    @NotBlank
    @Size(min = 10,  max = 50)
    @Schema(description = "The password of the user", example = "StrongPassword1234!", required = true)
    private String password;

    @Schema(description = "The status of the user account", example = "false", required = true)
    private Boolean enabled;

    @Schema(description = "The role of the user", example = "USER", required = true)
    private Role role;

    @Size(min = 2,  max = 30)
    @Schema(description = "The first name of the user", example = "Dummy")
    private String firstName;

    @Size(min = 2,  max = 30)
    @Schema(description = "The last name of the user", example = "User")
    private String lastName;

    @Schema(description = "The birthdate of the user", example = "1980-01-01T00:00:00.000Z")
    private Date dateOfBirth;

    @Schema(description = "The gender of the user", example = "MALE")
    private Gender gender;

    public UpdateUser(String username, String email, String password, Boolean enabled, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }
}
