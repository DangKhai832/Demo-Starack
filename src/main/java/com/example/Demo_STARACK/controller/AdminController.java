package com.example.Demo_STARACK.controller;

import com.example.Demo_STARACK.dto.request.AdminRequestDto;
import com.example.Demo_STARACK.dto.response.AdminResponseDto;
import com.example.Demo_STARACK.exception.DuplicateEntryException;
import com.example.Demo_STARACK.exception.InvalidCredentialsException;
import com.example.Demo_STARACK.service.AdminService;
import com.example.Demo_STARACK.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<AdminResponseDto> registerAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        AdminResponseDto newAdmin = adminService.registerAdmin(adminRequestDto);
        return ResponseEntity.ok(newAdmin);
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<String> handleDuplicateEntry(DuplicateEntryException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        String token = adminService.loginAdmin(adminRequestDto);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body("{\"token\": \"" + token + "\"}");
    }


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleDuplicateEntry(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
