package com.rikagu.streams.dtos.stream;

import jakarta.validation.constraints.NotBlank;
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
public class TranscribeStreamRequest implements Serializable {
    @NotBlank(message = "Id is required")
    UUID id;

    @NotBlank(message = "Title is required")
    String title;

    @NotBlank(message = "Description is required")
    String description;

    @NotBlank(message = "Transcript URL is required")
    String transcriptUrl;
}
