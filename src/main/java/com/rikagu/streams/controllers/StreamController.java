package com.rikagu.streams.controllers;

import com.rikagu.streams.components.Utils;
import com.rikagu.streams.dtos.stream.DeleteStreamRequest;
import com.rikagu.streams.dtos.stream.NewStreamRequest;
import com.rikagu.streams.entities.Stream;
import com.rikagu.streams.dtos.stream.UpdateStreamRequest;
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
    private final Utils utils;

    public StreamController(StreamRepository streamRepository, Utils utils) {
        this.streamRepository = streamRepository;
        this.utils = utils;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody NewStreamRequest request) {
        Stream existingStream = streamRepository.findByName(request.getName());
        if (existingStream != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A stream with the same name already exists");
        }

        final Stream newStream = Stream.builder()
                .name(request.getName())
                .streamUrl(request.getStreamUrl())
                .scheduleUrl(request.getScheduleUrl())
                .build();
        try {
            streamRepository.save(newStream);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the stream", e);
        }
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @RequestBody DeleteStreamRequest request) {
        Stream existingStream = streamRepository.findById(request.getId()).orElse(null);
        if (existingStream == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No stream with the given name exists");
        }

        try {
            streamRepository.delete(existingStream);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while deleting the stream", e);
        }
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UpdateStreamRequest request) {
        Stream existingStream = streamRepository.findById(request.getId()).orElse(null);
        if (existingStream == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No stream with the given name exists");
        }

        try {
            utils.copyNonNullProperties(existingStream, request);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while updating the stream", e);
        }

        try {
            streamRepository.save(existingStream);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while updating the stream", e);
        }
    }

    @GetMapping("/all")
    public List<Stream> getAll() {
        return streamRepository.findAll();
    }
}

