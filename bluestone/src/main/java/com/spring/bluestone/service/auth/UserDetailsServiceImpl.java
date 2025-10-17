package com.spring.bluestone.service.auth;

import com.spring.bluestone.entity.Role;
import com.spring.bluestone.entity.User;
import com.spring.bluestone.exception.type.UserNotFoundException;
import com.spring.bluestone.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUserName(username);

        return user.map(u -> org.springframework.security.core.userdetails.User.builder()
                .username(u.getUserName())
                .password(u.getPassword())
                .authorities(u.getRoles().stream()
                        .map(Role::getName)
                        .toArray(String[]::new))
                .build()
        ).orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }
}