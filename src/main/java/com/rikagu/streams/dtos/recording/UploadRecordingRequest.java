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
public class UploadRecordingRequest implements Serializable {
    @NotBlank(message = "Id is required")
    UUID id;

    @NotBlank(message = "Video URL is required")
    String videoUrl;

    @NotBlank(message = "Thumbnail URL is required")
    String thumbnailUrl;

    @NotBlank(message = "Duration is required")
    Integer duration;
}
