package com.example.Demo_STARACK.repository;

import com.example.Demo_STARACK.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, PagingAndSortingRepository<User, UUID> {
    // Phương thức tìm kiếm có hỗ trợ phân trang
    Page<User> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);
    boolean existsByEmail(String email);
}
