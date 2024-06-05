package com.rikagu.streams.dtos.recording;

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
public class StartRecordingRequest implements Serializable {
    @NotBlank(message = "Stream ID is required")
    UUID streamId;

    @NotBlank(message = "Stream URL is required")
    String streamUrl;
}
