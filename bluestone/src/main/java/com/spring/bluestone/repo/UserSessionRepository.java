package com.spring.bluestone.repo;

import com.spring.bluestone.entity.User;
import com.spring.bluestone.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByTokenAndIsActiveTrue(String token);
    Optional<UserSession> findByTokenAndIsActiveFalse(String token);
    List<UserSession> findByUserAndIsActiveTrue(User user);
}