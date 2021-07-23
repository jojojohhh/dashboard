package com.swlab.dashboard.repository.jdbc;

import com.swlab.dashboard.model.user.User;
import com.swlab.dashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class JdbcUserRepository implements UserRepository {

    @Autowired
    private JdbcTemplate jdbctemplate;

    @Override
    public Optional<User> findByEmail(String email) {
        jdbctemplate.queryForObject(
                "SELECT * FROM USER WHERE email=?",
                new Object[] {email},
                (rs, rowNum) -> Optional.of(new User(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("phoneNo")
                ))
        );
        return Optional.empty();
    }

    @Override
    public Optional<User> findWithUserRolesByEmailAndDel(String email, boolean del) {
        return Optional.empty();
    }
}
