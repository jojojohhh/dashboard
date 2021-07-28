package com.swlab.dashboard.repository;

import com.swlab.dashboard.model.user.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @EntityGraph(attributePaths = "userRoles")
    Optional<User> findWithUserRolesByEmailAndDel(String email, boolean del);
}
