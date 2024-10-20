package com.example.Demo_STARACK.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedUserResponseDto {
    private List<UserResponseDto> users;
    private int currentPage;
    private int totalPages;
    private long totalItems;
}
