package com.spring.bluestonewebhook.service.impl;

import com.spring.bluestonewebhook.dto.request.UserRegistrationRequest;
import com.spring.bluestonewebhook.entity.UserRegistrationLog;
import com.spring.bluestonewebhook.repo.UserRegistrationLogRepository;
import com.spring.bluestonewebhook.service.api.IUserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserRegistrationService implements IUserRegistrationService {

    private final UserRegistrationLogRepository userRegistrationLogRepository;

    @Override
    public void logRegistration(UserRegistrationRequest request) {
        UserRegistrationLog userRegistrationLog = UserRegistrationLog.builder()
                .userId(request.getUserId())
                .username(request.getUsername())
                .email(request.getEmail())
                .registeredAt(LocalDateTime.now())
                .source(request.getSource())
                .rolesAssigned(request.getRolesAssigned())
                .referrer(request.getReferrer())
                .build();

        userRegistrationLogRepository.save(userRegistrationLog);
    }
}
