package com.example.Demo_STARACK.controller;

import com.example.Demo_STARACK.entity.TrackLog;
import com.example.Demo_STARACK.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tracklogs")
public class TrackLogController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public List<TrackLog> getTrackLogs(@PathVariable UUID userId) {
        return userService.getTrackLogsByUserId(userId);
    }
}
