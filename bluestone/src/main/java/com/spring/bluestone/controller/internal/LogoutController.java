package com.spring.bluestone.controller.internal;

import com.spring.bluestone.dto.response.ApiResponse;
import com.spring.bluestone.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/private")
public class LogoutController {

    private final UserService userService;

    public LogoutController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            userService.logout(token);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "User logged out successfully", null));
        }
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid Authorization header", null), HttpStatus.BAD_REQUEST
        );
    }
}
