package com.example.Demo_STARACK.service;

import com.example.Demo_STARACK.dto.request.UserRequestDto;
import com.example.Demo_STARACK.dto.response.PaginatedUserResponseDto;
import com.example.Demo_STARACK.dto.response.UserResponseDto;
import com.example.Demo_STARACK.entity.TrackLog;
import com.example.Demo_STARACK.entity.User;
import com.example.Demo_STARACK.exception.CustomException;
import com.example.Demo_STARACK.repository.TrackLogRepository;
import com.example.Demo_STARACK.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TrackLogRepository trackLogRepository;

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // Lấy thông tin user theo ID
    public Optional<UserResponseDto> getUserById(UUID userId) {
        return userRepository.findById(userId).map(this::convertToResponseDto);
    }

    // Cập nhật thông tin user
    public UserResponseDto createUser(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException("Email already exists.");
        }

        User user = new User();
        user.setFullName(requestDto.getFullName());
        user.setEmail(requestDto.getEmail());
        user.setRole(requestDto.getRole());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setCreateAt(LocalDateTime.now());
        user.setStatus(requestDto.getStatus());

        User savedUser = userRepository.save(user);

        // Log the creation of new user fields
        logTrackChanges(null, savedUser);  // Pass oldUser as null for creation logs

        return convertToResponseDto(savedUser);
    }

    public UserResponseDto updateUser(UUID userId, UserRequestDto requestDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Store the old values before update
        User oldUser = new User();
        oldUser.setFullName(existingUser.getFullName());
        oldUser.setEmail(existingUser.getEmail());
        oldUser.setRole(existingUser.getRole());
        oldUser.setStatus(existingUser.getStatus());

        // Update new values
        existingUser.setFullName(requestDto.getFullName());
        existingUser.setEmail(requestDto.getEmail());
        existingUser.setRole(requestDto.getRole());
        existingUser.setStatus(requestDto.getStatus());

        User updatedUser = userRepository.save(existingUser);

        // Log changes
        logTrackChanges(oldUser, updatedUser);

        return convertToResponseDto(updatedUser);
    }

    public List<TrackLog> getTrackLogsByUserId(UUID userId) {
        return trackLogRepository.findByUserId(userId);
    }

    private void logTrackChanges(User oldUser, User newUser) {
        if (oldUser == null) {
            // Ghi log cho việc tạo mới
            trackLogRepository.save(TrackLog.builder()
                    .userId(newUser.getUserId())
                    .fieldName("user")  // Có thể đặt tên field là "user"
                    .oldValue(null)  // Không có giá trị cũ
                    .newValue("Created user with fullName: " + newUser.getFullName() + ", email: " + newUser.getEmail() + ", role: " + newUser.getRole() + ", status: " + newUser.getStatus())
                    .updatedAt(LocalDateTime.now())
                    .createAt(LocalDateTime.now())
                    .isCreate(true)  // Đánh dấu là tạo mới
                    .build());
        } else {
            // So sánh các giá trị cũ và mới, ghi log cho các thay đổi
            if (!oldUser.getFullName().equals(newUser.getFullName())) {
                trackLogRepository.save(TrackLog.builder()
                        .userId(newUser.getUserId())
                        .fieldName("fullName")
                        .oldValue(oldUser.getFullName())
                        .newValue(newUser.getFullName())
                        .updatedAt(LocalDateTime.now())
                        .isCreate(false)  // Đánh dấu là cập nhật
                        .build());
            }
            if (!oldUser.getEmail().equals(newUser.getEmail())) {
                trackLogRepository.save(TrackLog.builder()
                        .userId(newUser.getUserId())
                        .fieldName("email")
                        .oldValue(oldUser.getEmail())
                        .newValue(newUser.getEmail())
                        .updatedAt(LocalDateTime.now())
                        .isCreate(false)
                        .build());
            }
            if (!oldUser.getRole().equals(newUser.getRole())) {
                trackLogRepository.save(TrackLog.builder()
                        .userId(newUser.getUserId())
                        .fieldName("role")
                        .oldValue(oldUser.getRole())
                        .newValue(newUser.getRole())
                        .updatedAt(LocalDateTime.now())
                        .isCreate(false)
                        .build());
            }
            if (!oldUser.getStatus().equals(newUser.getStatus())) {
                trackLogRepository.save(TrackLog.builder()
                        .userId(newUser.getUserId())
                        .fieldName("status")
                        .oldValue(oldUser.getStatus())
                        .newValue(newUser.getStatus())
                        .updatedAt(LocalDateTime.now())
                        .isCreate(false)
                        .build());
            }
        }
    }

    // Xóa user
    public String deleteUser(UUID userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return "User deleted successfully"; // Thông báo thành công
        } else {
            throw new RuntimeException("User not found"); // Thông báo lỗi
        }
    }

    private UserResponseDto convertToResponseDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getUserId().toString()); // Chuyển UUID sang String
        responseDto.setFullName(user.getFullName());
        responseDto.setEmail(user.getEmail());
        responseDto.setRole(user.getRole());
        responseDto.setCreateAt(user.getCreateAt());
        responseDto.setStatus(user.getStatus());
        return responseDto;
    }

    public PaginatedUserResponseDto getUsersWithPagination(UserRequestDto requestDto) {
        // Tạo Pageable dựa trên page và size từ request
        PageRequest pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        // Gọi hàm phân trang từ repository
        Page<User> userPage = userRepository.findAll(pageable);

        // Chuyển đổi dữ liệu người dùng sang UserResponseDto
        PaginatedUserResponseDto paginatedResponse = new PaginatedUserResponseDto();
        paginatedResponse.setUsers(userPage.getContent().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList()));
        paginatedResponse.setCurrentPage(userPage.getNumber());
        paginatedResponse.setTotalPages(userPage.getTotalPages());
        paginatedResponse.setTotalItems(userPage.getTotalElements());

        return paginatedResponse;
    }

    public PaginatedUserResponseDto searchUsers(UserRequestDto requestDto) {
        int page = (requestDto.getPage() != null) ? requestDto.getPage() : 0;
        int size = (requestDto.getSize() != null) ? requestDto.getSize() : 10;

        // Tạo Pageable dựa trên page và size
        PageRequest pageable = PageRequest.of(page, size);

        Page<User> userPage;
        if (requestDto.getFullName() == null || requestDto.getFullName().isEmpty()) {
            // Nếu không có tên, lấy tất cả người dùng
            userPage = userRepository.findAll(pageable);
        } else {
            // Nếu có tên, tìm kiếm theo tên
            userPage = userRepository.findByFullNameContainingIgnoreCase(requestDto.getFullName(), pageable);
        }

        // Chuyển đổi dữ liệu người dùng sang UserResponseDto
        PaginatedUserResponseDto paginatedResponse = new PaginatedUserResponseDto();
        paginatedResponse.setUsers(userPage.getContent().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList()));
        paginatedResponse.setCurrentPage(userPage.getNumber());
        paginatedResponse.setTotalPages(userPage.getTotalPages());
        paginatedResponse.setTotalItems(userPage.getTotalElements());

        return paginatedResponse;
    }
}
