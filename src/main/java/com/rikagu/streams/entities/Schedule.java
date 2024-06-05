package com.rikagu.streams.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "schedules", indexes = {
        @Index(name = "idx_schedule_schedule_date", columnList = "schedule_date")
})
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String scheduleUrl;
    @Column
    private String channelName;

    @Column
    private Date uploadedAt;
    @Column
    private String scheduleDate;

    @PrePersist
    public void prePersist() {
        uploadedAt = new Date();
        ZoneId japanZone = ZoneId.of("Asia/Tokyo");
        ZonedDateTime japanTime = ZonedDateTime.now(japanZone);
        // Keep only the date; e.g. 2021-09-01T10:15:30+09:00 -> 2021-09-01
        scheduleDate = japanTime.toString().split("T")[0];
    }
}
