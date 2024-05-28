package com.rikagu.streams.repositories;

import com.rikagu.streams.entities.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StreamRepository extends JpaRepository<Stream, UUID> {
    Stream findByChannel(String name);
}
