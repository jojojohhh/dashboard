package com.swlab.dashboard.repository;

import com.swlab.dashboard.model.user.User;

import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    @EntityGraph(attributePaths = "userRoles")
    Optional<User> findWithUserRolesByEmailAndDel(String email, boolean del);
}
