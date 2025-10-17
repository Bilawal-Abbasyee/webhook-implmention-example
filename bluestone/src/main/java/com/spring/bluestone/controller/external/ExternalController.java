package com.spring.bluestone.controller.external;

import com.spring.bluestone.config.utils.JwtUtil;
import com.spring.bluestone.dto.request.UserRequest;
import com.spring.bluestone.dto.response.ApiResponse;
import com.spring.bluestone.entity.Role;
import com.spring.bluestone.entity.User;
import com.spring.bluestone.service.auth.UserDetailsServiceImpl;
import com.spring.bluestone.service.impl.RoleService;
import com.spring.bluestone.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
@Slf4j
public class ExternalController {

    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(ExternalController.class);

    public ExternalController(UserService userService, RoleService roleService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody UserRequest userRequest) {
        logger.info("Registering user: {}", userRequest.getUsername());
        User result = userService.saveOrUpdateUser(userRequest, null);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "User registered successfully", result));
    }

    @PostMapping("/createRole")
    public ResponseEntity<ApiResponse<List<Role>>> createRole(@RequestBody List<String> roleNames) {
        List<Role> result = roleService.saveRole(roleNames);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Roles added successfully", result));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserRequest user) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String token = jwtUtil.generateToken(userDetails.getUsername());
            userService.saveUserSession(userDetails.getUsername(), token);
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.OK.value(), "User logged in successfully", token), HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken  {}", e.getMessage());
            return new ResponseEntity<>(
                    new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "Invalid username or password", null), HttpStatus.UNAUTHORIZED
            );
        }
    }
}