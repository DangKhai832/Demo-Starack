package com.example.Demo_STARACK.repository;

import com.example.Demo_STARACK.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
    Admin findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
