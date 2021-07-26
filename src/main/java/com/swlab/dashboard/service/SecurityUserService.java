package com.swlab.dashboard.service;

import com.swlab.dashboard.model.user.SecurityUser;
import com.swlab.dashboard.model.user.User;
import com.swlab.dashboard.repository.Jpa.JpaUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUserService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(SecurityUserService.class);

    private final JpaUserRepository userRepository;

    @Autowired
    public SecurityUserService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findWithUserRolesByEmailAndDel(email, false);
        if (!user.isPresent()) {
            logger.info("존재하지 않는 이메일 입니다. : " + email);
            throw new UsernameNotFoundException(email);
        }
        return new SecurityUser(user.get());
    }
}