package com.swlab.dashboard.model.user;

import com.swlab.dashboard.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@DynamicInsert  @DynamicUpdate
public class User extends BaseEntity {

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 20, nullable = false)
    private String name;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;


    @Builder
    public User(String email, String name, String picture, UserRole userRole) {
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.userRole = userRole;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.userRole.getKey();
    }
}
