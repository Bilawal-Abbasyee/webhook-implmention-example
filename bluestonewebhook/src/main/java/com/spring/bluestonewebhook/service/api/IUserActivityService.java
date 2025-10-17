package com.spring.bluestonewebhook.service.api;


import com.spring.bluestonewebhook.dto.request.UserActivityRequest;

public interface IUserActivityService {
    void logActivity(UserActivityRequest request);
}
