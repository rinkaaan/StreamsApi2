package com.rikagu.streams.dtos.stream;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.rikagu.streams.entities.Stream}
 */
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class UpdateStreamRequest implements Serializable {
    @NotNull(message = "Stream ID is required")
    UUID id;
    String name;
    String streamUrl;
    String scheduleUrl;
}

