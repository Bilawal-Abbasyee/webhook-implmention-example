package com.spring.bluestonewebhook.repo;


import com.spring.bluestonewebhook.entity.UserRegistrationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationLogRepository extends JpaRepository<UserRegistrationLog, Long> {
    // Add custom queries if needed
}