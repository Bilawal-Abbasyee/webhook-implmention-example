package com.spring.bluestonewebhook.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_activity_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "activity_type", nullable = false)
    private String activityType; // LOGIN or LOGOUT

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "location")
    private String location;
}