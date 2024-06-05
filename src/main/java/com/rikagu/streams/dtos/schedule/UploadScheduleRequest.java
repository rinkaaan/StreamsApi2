package com.rikagu.streams.dtos.schedule;

import com.rikagu.streams.annotations.ValidEnum;
import com.rikagu.streams.primitives.Channel;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.rikagu.streams.entities.Recording}
 */
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class UploadScheduleRequest implements Serializable {
    @NotBlank(message = "Channel name is required")
    @ValidEnum(enumClass = Channel.class, message = "Invalid channel name")
    String channelName;
}
