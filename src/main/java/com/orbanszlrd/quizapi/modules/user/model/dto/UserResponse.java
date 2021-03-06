package com.orbanszlrd.quizapi.modules.user.model.dto;

import com.orbanszlrd.quizapi.modules.user.model.Gender;
import com.orbanszlrd.quizapi.modules.user.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class UserResponse {
    @Schema(description = "The id of the user", example = "1", required = true)
    private Long id;

    @NotBlank
    @Size(min = 6, max = 20)
    @Schema(description = "The username of the user", example = "dummy.user", required = true)
    private String username;

    @NotBlank
    @Email
    @Size(min = 10, max = 50)
    @Schema(description = "The email of the user", example = "dummy@email.com", required = true)
    private String email;

    @Schema(description = "The status of the user account", example = "true", required = true)
    private boolean enabled;

    @Schema(description = "The role of the user", example = "USER", required = true)
    private Role role;

    @Size(min = 2, max = 30)
    @Schema(description = "The first name of the user", example = "Dummy")
    private String firstName;

    @Size(min = 2, max = 30)
    @Schema(description = "The last name of the user", example = "User")
    private String lastName;

    @Schema(description = "The birthdate of the user", example = "1980-01-01T00:00:00.000Z")
    private Date dateOfBirth;

    @Schema(description = "The gender of the user", example = "MALE")
    private Gender gender;

    @Schema(description = "The creation timestamp of the user", example = "2021-12-31T00:00:00.000Z")
    private Timestamp createdAt;

    @Schema(description = "The last update timestamp of the user", example = "2021-12-31T00:00:00.000Z")
    private Timestamp updatedAt;

    public UserResponse(Long id, String username, String email, boolean enabled, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }
}
