package com.rikagu.streams.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "recordings")
public class Recording {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Start stream
    @Column
    private String streamName;
    @Column
    private String streamUrl;

    // Upload stream
    @Column
    private String videoUrl;
    @Column
    private String thumbnailUrl;
    @Column
    private Integer duration;
    @Column
    private Date uploadedAt;

    // Transcribe stream
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String transcriptUrl;

    @PrePersist
    public void prePersist() {
        uploadedAt = new Date();
    }
}
