package com.spring.bluestonewebhook.service.api;


import com.spring.bluestonewebhook.dto.request.WebhookEventRequest;

public interface IWebhookService {
    void logEvent(WebhookEventRequest request);
}