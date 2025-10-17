package com.spring.bluestonewebhook.service.impl;



import com.spring.bluestonewebhook.dto.request.WebhookEventRequest;
import com.spring.bluestonewebhook.entity.WebhookEvent;
import com.spring.bluestonewebhook.repo.WebhookEventRepository;
import com.spring.bluestonewebhook.service.api.IWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebhookService implements IWebhookService {

    private final WebhookEventRepository webhookEventRepository;


    @Override
    public void logEvent(WebhookEventRequest request) {
        WebhookEvent event = WebhookEvent.builder()
                .eventType(request.getEventType())
                .sourceIp(request.getSourceIp())
                .receivedAt(LocalDateTime.now())
                .payload(request.getPayload())
                .status(request.getStatus())
                .processingNotes(request.getProcessingNotes())
                .build();

        webhookEventRepository.save(event);
    }
}