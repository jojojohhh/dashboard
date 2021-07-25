package com.swlab.dashboard.repository.jdbc;

import com.swlab.dashboard.model.user.User;
import com.swlab.dashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

public class JdbcUserRepository implements UserRepository {

    @Autowired
    private JdbcTemplate jdbctemplate;

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(jdbctemplate.query(
                "SELECT * FROM USER WHERE email=?",
                userRowMapper(),
                email
        ).stream().findAny().orElseThrow(NotFoundException::new));
    }

    @Override
    public Optional<User> findWithUserRolesByEmailAndDel(String email, boolean del) {
        return Optional.ofNullable(jdbctemplate.query(
                "SELECT * FROM USER WHERE email=?, del=? ",
                userRowMapper(),
                email,
                del
        ).stream().findAny().orElseThrow(NotFoundException::new));
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            return new User(
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("phoneNo")
            );
        };
    }
}
