package com.swlab.dashboard.repository;

import com.swlab.dashboard.config.TestConfig;
import com.swlab.dashboard.model.user.User;
import com.swlab.dashboard.model.user.UserRole;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Rollback
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private User setUser;

    private UserRole setUserRole;

    @BeforeAll
    public void setUp() {
        setUser = userRepository.save(User.builder()
                .email("test@test.test")
                .password("test123")
                .name("test")
                .phoneNo("1234567890")
                .build());

        setUserRole = userRoleRepository.save(UserRole.builder()
                .user(setUser)
                .roleType(UserRole.RoleType.USER)
                .build());
    }

    @Test
    public void findByEmailTrue() {
        User user = userRepository.findByEmail(setUser.getEmail()).get();
        Assertions.assertEquals(setUser.getEmail(), user.getEmail());
    }

    @Test
    public void findByEmailFalse() {
        Assertions.assertFalse(userRepository.findByEmail("testtest@test123.com").isPresent());
    }

    @Test
    public void findWithUserRolesByEmailAndDel_DelIsFalse() {
        User user = userRepository.findWithUserRolesByEmailAndDel(setUser.getEmail(), false).get();
        Assertions.assertEquals(setUser.getEmail(), user.getEmail());
    }
}
