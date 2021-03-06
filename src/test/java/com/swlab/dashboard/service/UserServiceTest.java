package com.swlab.dashboard.service;

import com.swlab.dashboard.dto.UserDto;
import com.swlab.dashboard.model.user.User;
import com.swlab.dashboard.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@Slf4j
public class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private PasswordEncoder passwordEncoder;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        userDto = UserDto.builder()
                .email("test@test.com")
                .password("test123")
                .name("test")
                .phoneNo("1234567890")
                .build();
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void save() {
        when(userRepository.save(any(User.class))).then(AdditionalAnswers.returnsFirstArg());

        User user = userService.save(userDto);

        assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
    }
}
