package com.spring.bluestone.service.impl;

import com.spring.bluestone.dto.request.UserRequest;
import com.spring.bluestone.entity.User;
import com.spring.bluestone.entity.UserSession;
import com.spring.bluestone.exception.type.InvalidRequestException;
import com.spring.bluestone.exception.type.UserNotFoundException;
import com.spring.bluestone.repo.RoleRepository;
import com.spring.bluestone.repo.UserRepository;
import com.spring.bluestone.repo.UserSessionRepository;
import com.spring.bluestone.service.api.IUserService;
import com.spring.bluestone.util.ErrorMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserSessionRepository userSessionRepository;
    private final TransactionTemplate transactionTemplate;
    private final WebhookClient webhookClient;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       UserSessionRepository userSessionRepository,
                       TransactionTemplate transactionTemplate,
                       WebhookClient webhookClient) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userSessionRepository = userSessionRepository;
        this.transactionTemplate = transactionTemplate;
        this.webhookClient = webhookClient;
    }

    @Override
    public User saveOrUpdateUser(UserRequest req, Long id) {
        if (req == null || req.getUsername() == null || req.getPassword() == null || req.getEmail() == null) {
            throw new InvalidRequestException(ErrorMessageUtil.INVALID_USER_REQUEST);
        }

        User user = (id == null)
                ? new User()
                : userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessageUtil.USER_NOT_FOUND_PREFIX + id));

        user.setUserName(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setEmail(req.getEmail());

        if (req.getRoleIds() != null && !req.getRoleIds().isEmpty()) {
            user.setRoles(roleRepository.findAllById(req.getRoleIds()));
        }

        User savedUser = userRepository.save(user);

        // 🔔 Trigger webhook for registration
        if (id == null) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("userId", savedUser.getUserId());
            payload.put("username", savedUser.getUserName());
            payload.put("email", savedUser.getEmail());
            payload.put("rolesAssigned", req.getRoleIds());
            payload.put("source", "WEB");
            webhookClient.sendEvent("/user-registration", payload);
        }

        return savedUser;
    }

    @Override
    public User deleteUser(Long id) {
        if (id == null) {
            throw new InvalidRequestException("User id must not be null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessageUtil.USER_NOT_FOUND_PREFIX + id));

        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new InvalidRequestException("User id must not be null");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessageUtil.USER_NOT_FOUND_PREFIX + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUserSession(String username, String token) {
        transactionTemplate.execute(status -> {
            try {
                User user = userRepository.findByUserName(username)
                        .orElseThrow(() -> new UserNotFoundException(ErrorMessageUtil.USER_NOT_FOUND_PREFIX + username));

                List<UserSession> activeSessions = userSessionRepository.findByUserAndIsActiveTrue(user);
                for (UserSession session : activeSessions) {
                    session.setIsActive(false);
                    userSessionRepository.save(session);
                }

                UserSession newSession = UserSession.builder()
                        .user(user)
                        .token(token)
                        .issuedAt(LocalDateTime.now())
                        .expiresAt(LocalDateTime.now().plusMinutes(50))
                        .isActive(true)
                        .build();

                UserSession savedSession = userSessionRepository.save(newSession);

                // 🔔 Trigger webhook for login
                Map<String, Object> payload = new HashMap<>();
                payload.put("userId", user.getUserId());
                payload.put("username", user.getUserName());
                payload.put("activityType", "LOGIN");
                payload.put("ipAddress", "N/A"); // Replace with actual IP if available
                webhookClient.sendEvent("/user-activity", payload);

                return savedSession;
            } catch (Exception e) {
                status.setRollbackOnly();
                log.error("Failed to save user session for {}: {}", username, e.getMessage(), e);
                throw e;
            }
        });
    }

    public void logout(String token) {
        transactionTemplate.execute(status -> {
            try {
                Optional<UserSession> sessionOpt = userSessionRepository.findByTokenAndIsActiveTrue(token);
                sessionOpt.ifPresent(session -> {
                    session.setIsActive(false);
                    userSessionRepository.save(session);

                    // 🔔 Trigger webhook for logout
                    Map<String, Object> payload = new HashMap<>();
                    payload.put("userId", session.getUser().getUserId());
                    payload.put("username", session.getUser().getUserName());
                    payload.put("activityType", "LOGOUT");
                    payload.put("ipAddress", "N/A");
                    webhookClient.sendEvent("/user-activity", payload);
                });
                return true;
            } catch (Exception e) {
                status.setRollbackOnly();
                log.error("Failed to logout session for token {}: {}", token, e.getMessage(), e);
                throw e;
            }
        });
    }
}