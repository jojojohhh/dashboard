package com.swlab.dashboard.repository.Jpa;

import com.swlab.dashboard.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRoleRepository extends JpaRepository<UserRole, Long> {
}
