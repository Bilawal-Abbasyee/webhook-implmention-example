package com.spring.bluestone.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WebhookClient {

    private final RestTemplate restTemplate;

    @Value("${webhook.receiver.url}")
    private String webhookReceiverUrl;

    @Value("${webhook.auth.token}")
    private String webhookAuthToken;

    public void sendEvent(String endpointPath, Object payload) {
        String fullUrl = webhookReceiverUrl + endpointPath;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(webhookAuthToken);

        HttpEntity<Object> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(fullUrl, request, String.class);
            // Optionally log or handle response
        } catch (Exception e) {
            // Optionally log or handle failure
            e.printStackTrace();
        }
    }
}

