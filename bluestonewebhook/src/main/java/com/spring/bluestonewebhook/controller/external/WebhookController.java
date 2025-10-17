package com.spring.bluestonewebhook.controller.external;

import com.spring.bluestonewebhook.dto.request.UserActivityRequest;
import com.spring.bluestonewebhook.dto.request.UserRegistrationRequest;
import com.spring.bluestonewebhook.dto.request.WebhookEventRequest;
import com.spring.bluestonewebhook.service.api.IUserActivityService;
import com.spring.bluestonewebhook.service.api.IUserRegistrationService;
import com.spring.bluestonewebhook.service.api.IWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final IWebhookService webhookService;
    private final IUserActivityService userActivityService;
    private final IUserRegistrationService userRegistrationService;

    @PostMapping("/event")
    public ResponseEntity<String> receiveGenericEvent(
            @RequestBody WebhookEventRequest request
    ) {
        webhookService.logEvent(request);
        return ResponseEntity.ok("Generic webhook received");
    }

    @PostMapping("/user-activity")
    public ResponseEntity<String> receiveUserActivity(@RequestBody UserActivityRequest request
    ) {
        userActivityService.logActivity(request);
        return ResponseEntity.ok("User activity logged");
    }

    @PostMapping("/user-registration")
    public ResponseEntity<String> receiveUserRegistration(@RequestBody UserRegistrationRequest request
    ) {
        userRegistrationService.logRegistration(request);
        return ResponseEntity.ok("User registration logged");
    }
}
