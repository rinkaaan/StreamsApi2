package com.rikagu.streams.controllers;

import com.rikagu.streams.dtos.stream.StartStreamRequest;
import com.rikagu.streams.dtos.stream.StartStreamResponse;
import com.rikagu.streams.dtos.stream.TranscribeStreamRequest;
import com.rikagu.streams.dtos.stream.UploadStreamRequest;
import com.rikagu.streams.entities.Stream;
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
@RequestMapping("/api/stream")
public class StreamController {

    private final StreamRepository streamRepository;

    public StreamController(StreamRepository streamRepository) {
        this.streamRepository = streamRepository;
    }

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public StartStreamResponse create(@Valid @RequestBody StartStreamRequest request) {
        Stream existingStream = streamRepository.findByChannel(request.getChannel());
        if (existingStream != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A stream with the same name already exists");
        }

        final Stream newStream = Stream.builder()
                .channel(request.getChannel())
                .streamUrl(request.getStreamUrl())
                .build();
        try {
            streamRepository.save(newStream);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the stream", e);
        }

        return StartStreamResponse.builder()
                .id(newStream.getId())
                .build();
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upload(@Valid @RequestBody UploadStreamRequest request) {
        Stream existingStream = streamRepository.findById(request.getId()).orElse(null);
        if (existingStream == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No stream with the given name exists");
        }

        existingStream.setVideoUrl(request.getVideoUrl());
        existingStream.setThumbnailUrl(request.getThumbnailUrl());
        existingStream.setDuration(request.getDuration());

        try {
            streamRepository.save(existingStream);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the stream", e);
        }
    }

    @PostMapping("/transcribe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transcribe(@Valid @RequestBody TranscribeStreamRequest request) {
        Stream existingStream = streamRepository.findById(request.getId()).orElse(null);
        if (existingStream == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No stream with the given name exists");
        }

        existingStream.setTitle(request.getTitle());
        existingStream.setDescription(request.getDescription());
        existingStream.setTranscriptUrl(request.getTranscriptUrl());

        try {
            streamRepository.save(existingStream);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the stream", e);
        }
    }

    @GetMapping("/all")
    public List<Stream> getAllStreams() {
        return streamRepository.findAll();
    }
}

