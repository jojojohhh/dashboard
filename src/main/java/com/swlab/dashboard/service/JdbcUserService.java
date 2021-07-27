package com.swlab.dashboard.service;

import com.swlab.dashboard.dto.UserDto;
import com.swlab.dashboard.model.user.User;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JdbcUserService {

    private final JdbcUserRepository jdbcUserRepository;
    private final JdbcUserRoleRepository jdbcUserRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return jdbcUserRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public User save(UserDto userDto){
        return jdbcUserRepository.save(User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .phoneNo(userDto.getPhoneNo())
                .build());
    }
}
