package com.rikagu.streams.dtos.stream;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.rikagu.streams.entities.Recording}
 */
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class NewStreamRequest implements Serializable {
    @NotBlank(message = "Name is required")
    String name;

    @NotBlank(message = "Stream URL is required")
    String streamUrl;

    String scheduleUrl;
}
