package com.spring.bluestone.controller.internal;

import com.spring.bluestone.dto.request.UserRequest;
import com.spring.bluestone.dto.response.ApiResponse;
import com.spring.bluestone.entity.User;
import com.spring.bluestone.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/private")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long id) {
        User result = userService.saveOrUpdateUser(userRequest, id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "User registered successfully", result));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id) {
        User result = userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "User deleted successfully", result));
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "User retrieved successfully", user));
    }

    @GetMapping("/admin/getAllUsers")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "All users retrieved successfully", users));
    }
}