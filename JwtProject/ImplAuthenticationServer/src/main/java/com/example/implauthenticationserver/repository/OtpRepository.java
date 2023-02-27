package com.example.implauthenticationserver.repository;

import com.example.implauthenticationserver.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, String> {
    Optional<Otp> findByUsername(String username);
}
