package com.example.Demo_STARACK.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Sử dụng AUTO để tự động tạo giá trị
    private UUID userId; // UUID

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role; // Super Admin, Trainer, Member

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private String status;// ACTIVATED, WAITING

}

