package com.spring.bluestone.service;

import com.spring.bluestone.dto.request.UserRequest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(UserRequest.builder()
                        .username("testuser1")
                        .password("password1")
                        .email("testUser@gmail.com")
                        .roleIds(new ArrayList<>(Arrays.asList(1L, 2L, 3L))).build()
                ),
                Arguments.of(UserRequest.builder()
                        .username(null)
                        .password("password3")
                        .email("testUser1@gmail.com")
                        .roleIds(new ArrayList<>(Arrays.asList(1L, 2L, 3L))).build()
                )
        );


    }
}
