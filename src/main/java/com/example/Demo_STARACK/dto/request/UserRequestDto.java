package com.example.Demo_STARACK.dto.request;

import lombok.Data;

@Data
public class UserRequestDto {
    private String fullName;
    private String email;
    private String role; // Super Admin, Trainer, Member
    private String password; // Super Admin, Trainer, Member
    private String status; // ACTIVATED, WAITING
    private Integer page; // Thay đổi từ int sang Integer
    private Integer size;

}
