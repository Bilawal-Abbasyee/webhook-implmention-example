package com.spring.bluestonewebhook.dto.request;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationRequest {
    private String userId;
    private String username;
    private String email;
    private String source; // e.g., WEB, MOBILE
    private String rolesAssigned;
    private String referrer;
}
