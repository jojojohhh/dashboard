package com.swlab.dashboard.repository;

import com.swlab.dashboard.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
