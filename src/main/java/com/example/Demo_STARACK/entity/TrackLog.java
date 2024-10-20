package com.example.Demo_STARACK.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "track_log") // Tên bảng sẽ là "track_log"
public class TrackLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID userId; // Change to UUID
    private String fieldName;
    private String oldValue;
    private String newValue;
    private LocalDateTime updatedAt;
    private LocalDateTime createAt;
    private boolean isCreate;
}

