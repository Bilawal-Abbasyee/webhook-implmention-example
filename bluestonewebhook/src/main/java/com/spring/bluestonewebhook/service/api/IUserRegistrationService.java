package com.spring.bluestonewebhook.service.api;


import com.spring.bluestonewebhook.dto.request.UserRegistrationRequest;

public interface IUserRegistrationService {
    void logRegistration(UserRegistrationRequest request);
}