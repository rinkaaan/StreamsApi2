package com.rikagu.streams.dtos.stream;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.rikagu.streams.entities.Stream}
 */
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class StartStreamRequest implements Serializable {
    @NotBlank(message = "Channel is required")
    String channel;

    @NotBlank(message = "Stream URL is required")
    String streamUrl;
}
