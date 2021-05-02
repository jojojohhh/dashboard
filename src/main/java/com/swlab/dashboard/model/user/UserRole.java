package com.swlab.dashboard.model.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swlab.dashboard.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_type"})})
@DynamicUpdate
public class UserRole extends BaseEntity implements GrantedAuthority {

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ROLE_USER"))
    private User user;

    @Column(name = "role_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.roleType.name();
    }

    public enum RoleType {
        USER, ADMIN
    }

    @Builder
    public UserRole(User user, RoleType roleType) {
        this.user = user;
        this.roleType = roleType;
    }
}
