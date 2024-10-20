package com.example.Demo_STARACK.controller;

import com.example.Demo_STARACK.dto.request.UserRequestDto;
import com.example.Demo_STARACK.dto.response.PaginatedUserResponseDto;
import com.example.Demo_STARACK.dto.response.UserResponseDto;
import com.example.Demo_STARACK.exception.CustomException;
import com.example.Demo_STARACK.exception.UserNotFoundException;
import com.example.Demo_STARACK.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto userRequestDto) {
        try {
            UserResponseDto newUser = userService.createUser(userRequestDto);
            return ResponseEntity.ok(newUser);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Trả về thông báo lỗi
        }
    }


    @PostMapping("/paginated")
    public PaginatedUserResponseDto getUsersWithPagination(@RequestBody UserRequestDto requestDto) {
        return userService.getUsersWithPagination(requestDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) { // Sửa kiểu dữ liệu thành UUID
        UserResponseDto user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedUserResponseDto> searchUsers(
            @RequestBody UserRequestDto requestDto
    ) {
        return ResponseEntity.ok(userService.searchUsers(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID id, @RequestBody UserRequestDto userRequestDto) { // Sửa kiểu dữ liệu thành UUID
        UserResponseDto updatedUser = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable UUID id) {
        try {
            String responseMessage = userService.deleteUser(id);
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", responseMessage);
            return ResponseEntity.ok(responseBody); // Trả về 200 OK với message thành công
        } catch (UserNotFoundException e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody); // Trả về 404 với message lỗi
        }
    }


}
