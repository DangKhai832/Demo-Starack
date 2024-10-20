package com.example.Demo_STARACK.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private String id; // UUID dưới dạng String
    private String fullName;
    private String email;
    private String role;
    private LocalDateTime createAt; // Thời gian tạo
    private String status;


}
