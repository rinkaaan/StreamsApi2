package com.rikagu.streams.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.rikagu.streams.entities.User}
 */
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class LoginUserRequest implements Serializable {
    @NotBlank(message = "Email or username is required")
    String usernameOrEmail;

    @NotBlank(message = "Password is required")
    String password;
}
