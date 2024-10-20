package com.example.Demo_STARACK.service;

import com.example.Demo_STARACK.dto.request.AdminRequestDto;
import com.example.Demo_STARACK.dto.response.AdminResponseDto;
import com.example.Demo_STARACK.entity.Admin;
import com.example.Demo_STARACK.exception.DuplicateEntryException;
import com.example.Demo_STARACK.exception.InvalidCredentialsException;
import com.example.Demo_STARACK.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    // Đăng ký admin mới
    public AdminResponseDto registerAdmin(AdminRequestDto requestDto) {
        if (adminRepository.existsByUsername(requestDto.getUsername())) {
            throw new DuplicateEntryException("Username đã tồn tại.");
        }
        if (adminRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateEntryException("Email đã tồn tại.");
        }

        Admin admin = new Admin();
        admin.setUsername(requestDto.getUsername());
        admin.setPassword(passwordEncoder.encode(requestDto.getPassword())); // Mã hóa mật khẩu
        admin.setEmail(requestDto.getEmail());

        Admin savedAdmin = adminRepository.save(admin);
        return convertToResponseDto(savedAdmin);
    }


    public String loginAdmin(AdminRequestDto adminRequestDto) {
        Admin admin = adminRepository.findByEmail(adminRequestDto.getEmail());
        if (admin == null) {
            throw new InvalidCredentialsException("Tài khoản không tồn tại."); // Tài khoản không tồn tại
        }

        // So sánh mật khẩu đã mã hóa
        if (!passwordEncoder.matches(adminRequestDto.getPassword(), admin.getPassword())) {
            throw new InvalidCredentialsException("Mật khẩu không đúng."); // Mật khẩu không đúng
        }

        // Nếu đăng nhập thành công, tạo và trả về token
        return tokenService.generateToken(adminRequestDto.getEmail());
    }

    private AdminResponseDto convertToResponseDto(Admin admin) {
        AdminResponseDto responseDto = new AdminResponseDto();
        responseDto.setId(admin.getId());
        responseDto.setUsername(admin.getUsername());
        responseDto.setEmail(admin.getEmail());
        return responseDto;
    }
}
