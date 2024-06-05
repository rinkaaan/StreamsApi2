package com.rikagu.streams.controllers;

import com.rikagu.streams.dtos.recording.StartRecordingRequest;
import com.rikagu.streams.dtos.recording.StartRecordingResponse;
import com.rikagu.streams.dtos.recording.TranscribeRecordingRequest;
import com.rikagu.streams.dtos.recording.UploadRecordingRequest;
import com.rikagu.streams.entities.Recording;
import com.rikagu.streams.entities.Stream;
import com.rikagu.streams.repositories.RecordingRepository;
import com.rikagu.streams.repositories.StreamRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/recording")
public class RecordingController {

    private final RecordingRepository recordingRepository;
    private final StreamRepository streamRepository;

    public RecordingController(RecordingRepository recordingRepository, StreamRepository streamRepository) {
        this.recordingRepository = recordingRepository;
        this.streamRepository = streamRepository;
    }

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public StartRecordingResponse start(@Valid @RequestBody StartRecordingRequest request) {
        Stream stream = streamRepository.findById(request.getStreamId()).orElse(null);
        if (stream == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No stream with the given ID exists");
        }

        final Recording newStream = Recording.builder()
                .streamName(stream.getName())
                .streamUrl(stream.getStreamUrl())
                .build();
        try {
            recordingRepository.save(newStream);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the stream", e);
        }

        return StartRecordingResponse.builder()
                .id(newStream.getId())
                .build();
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upload(@Valid @RequestBody UploadRecordingRequest request) {
        Recording existingRecording = recordingRepository.findById(request.getId()).orElse(null);
        if (existingRecording == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No stream with the given name exists");
        }

        existingRecording.setVideoUrl(request.getVideoUrl());
        existingRecording.setThumbnailUrl(request.getThumbnailUrl());
        existingRecording.setDuration(request.getDuration());

        try {
            recordingRepository.save(existingRecording);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the stream", e);
        }
    }

    @PostMapping("/transcribe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transcribe(@Valid @RequestBody TranscribeRecordingRequest request) {
        Recording existingRecording = recordingRepository.findById(request.getId()).orElse(null);
        if (existingRecording == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No stream with the given name exists");
        }

        existingRecording.setTitle(request.getTitle());
        existingRecording.setDescription(request.getDescription());
        existingRecording.setTranscriptUrl(request.getTranscriptUrl());

        try {
            recordingRepository.save(existingRecording);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the stream", e);
        }
    }

    @GetMapping("/all")
    public List<Recording> getAllStreams() {
        return recordingRepository.findAll();
    }
}

