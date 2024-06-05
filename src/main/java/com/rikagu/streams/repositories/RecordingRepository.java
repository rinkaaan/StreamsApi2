package com.rikagu.streams.repositories;

import com.rikagu.streams.entities.Recording;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecordingRepository extends JpaRepository<Recording, UUID> {
}
