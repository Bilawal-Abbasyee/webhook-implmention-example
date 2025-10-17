package com.spring.bluestonewebhook.dto.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebhookEventRequest {
    private String eventType;
    private String sourceIp;
    private String payload;
    private String status;
    private String processingNotes;
}