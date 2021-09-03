package com.swlab.dashboard.service;

import com.swlab.dashboard.dto.UserDto;
import com.swlab.dashboard.model.user.User;
import com.swlab.dashboard.model.user.UserRole;

import com.swlab.dashboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User save(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .phoneNo(userDto.getPhoneNo())
                .userRole(UserRole.USER)
                .build();
        return userRepository.save(user);
    }
}
