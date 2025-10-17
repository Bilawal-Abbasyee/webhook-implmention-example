package com.spring.bluestone.service;

import com.spring.bluestone.dto.request.UserRequest;
import com.spring.bluestone.repo.UserRepository;
import com.spring.bluestone.service.impl.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @Test
    void getUserByIdTest() {
        //    for testing only I have disabled this test
        assertNotNull(
                userRepository.findById(1L).orElse(null)
        );
    }

    @ParameterizedTest
    @ValueSource(
            longs = {1L, 2L, 3L}
    )
    void getUserByIdParameterizedTest(Long id) {
        assertNotNull(
                userRepository.findById(id).orElse(null)
        );
    }

    @ParameterizedTest
    @CsvSource(
            {"1, Bilawal", "3, Ishfaque","4, Barkat"}
    )
    void getUserByIdAndNameParameterizedTest(Long id, String name) {
        assertEquals(Objects.requireNonNull(userRepository.findById(id).orElse(null)).getUserName(), name);
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    void saveUserParameterizedTest(UserRequest userRequest) {
        assertNotNull(
                userService.saveOrUpdateUser(userRequest, null)
        );
    }
}
