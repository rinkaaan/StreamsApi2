package com.rikagu.streams.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.rikagu.streams.entities.User}
 */
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ResendVerificationEmailRequest implements Serializable {
    @NotBlank(message = "Email or username is required")
    String usernameOrEmail;

    public enum ResetType {
        RESET_PASSWORD, NEW_ACCOUNT
    }

    @NotNull(message = "Reset type is required")
    ResetType resetType;
}
