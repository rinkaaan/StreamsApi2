package com.rikagu.streams.controllers;

import com.rikagu.streams.annotations.ValidEnum;
import com.rikagu.streams.entities.Schedule;
import com.rikagu.streams.primitives.Channel;
import com.rikagu.streams.repositories.ScheduleRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleRepository scheduleRepository;
    private final MinioClient minioClient;

    public ScheduleController(ScheduleRepository scheduleRepository, MinioClient minioClient) {
        this.scheduleRepository = scheduleRepository;
        this.minioClient = minioClient;
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadSchedule(
            @RequestParam
            MultipartFile file,
            @Validated
            @RequestParam
            @ValidEnum(enumClass = Channel.class, message = "Invalid channel name")
            String channelName
    ) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }
        String contentType = file.getContentType();
        List<String> ALLOWED_CONTENT_TYPES = List.of("application/json");
        if (!ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type. Only JSON files are allowed.");
        }

        String objectPath = Paths.get(channelName, "schedule.json").toString();
        try {
            String bucketName = "streams";
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectPath)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading file to MinIO", e);
        }

        Schedule schedule = Schedule.builder()
            .channelName(channelName)
            .scheduleUrl(objectPath)
            .scheduleDate(getCurrentDateInJapan())
            .build();
        try {
            scheduleRepository.save(schedule);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the schedule", e);
        }
    }

    private String getCurrentDateInJapan() {
        ZoneId japanZoneId = ZoneId.of("Asia/Tokyo");
        LocalDate currentDateInJapan = LocalDate.now(japanZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDateInJapan.format(formatter);
    }
}

