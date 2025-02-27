package com.thc.winterdemo.repository;

import com.thc.winterdemo.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByContent(String refreshToken);
    List<RefreshToken> findByUserId(Long userId);
}
