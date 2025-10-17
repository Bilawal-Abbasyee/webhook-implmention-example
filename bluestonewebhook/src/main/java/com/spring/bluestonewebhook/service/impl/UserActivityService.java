package com.spring.bluestonewebhook.service.impl;


import com.spring.bluestonewebhook.dto.request.UserActivityRequest;
import com.spring.bluestonewebhook.entity.UserActivityLog;
import com.spring.bluestonewebhook.repo.UserActivityLogRepository;
import com.spring.bluestonewebhook.service.api.IUserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserActivityService implements IUserActivityService {

    private final UserActivityLogRepository userActivityLogRepository;

    @Override
    public void logActivity(UserActivityRequest request) {
        UserActivityLog log = UserActivityLog.builder()
                .userId(request.getUserId())
                .username(request.getUsername())
                .activityType(request.getActivityType())
                .ipAddress(request.getIpAddress())
                .deviceInfo(request.getDeviceInfo())
                .location(request.getLocation())
                .timestamp(LocalDateTime.now())
                .build();

        userActivityLogRepository.save(log);
    }
}
