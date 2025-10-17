package com.spring.bluestonewebhook.dto.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserActivityRequest {
    private String userId;
    private String username;
    private String activityType; // LOGIN or LOGOUT
    private String ipAddress;
    private String deviceInfo;
    private String location;
}