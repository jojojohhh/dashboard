package com.swlab.dashboard.repository;

import com.swlab.dashboard.model.user.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @EntityGraph(attributePaths = "userRole")
    Optional<User> findWithUserRoleByEmailAndDel(String email, boolean del);
}
