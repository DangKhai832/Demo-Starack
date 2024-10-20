package com.example.Demo_STARACK.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Auto incremented ID in the database

    @Column(nullable = false, unique = true)
    private String username; // Tên đăng nhập của admin

    @Column(nullable = false)
    private String password; // Mật khẩu đã được mã hóa

    @Column(nullable = false, unique = true)
    private String email; // Email của admin
}
