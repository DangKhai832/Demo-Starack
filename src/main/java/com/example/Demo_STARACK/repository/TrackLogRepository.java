package com.example.Demo_STARACK.repository;

import com.example.Demo_STARACK.entity.TrackLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrackLogRepository extends JpaRepository<TrackLog, Long> {
    List<TrackLog> findByUserId(UUID userId);
}
