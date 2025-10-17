package com.spring.bluestonewebhook.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "webhook_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebhookEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_type", nullable = false)
    private String eventType; // e.g., LOGIN, LOGOUT, REGISTER

    @Column(name = "source_ip")
    private String sourceIp;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    @Lob
    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Column(name = "status")
    private String status; // e.g., RECEIVED, PROCESSED, FAILED

    @Column(name = "processing_notes")
    private String processingNotes;
}
