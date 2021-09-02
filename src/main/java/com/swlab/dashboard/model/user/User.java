package com.swlab.dashboard.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swlab.dashboard.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@DynamicInsert  @DynamicUpdate
public class User extends BaseEntity {

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 150, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 11, nullable = false)
    private String phoneNo;

    @Column
    private String picture;

    @Singular("userRoles")
    @JsonIgnoreProperties({"createTimeStamp", "del"})
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    @Where(clause = "del = false")
    private Set<UserRole> userRoles;


    @Builder
    public User(String email, String password, String name, String phoneNo,UserRole userRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNo = phoneNo;
        this.userRoles.add(userRole);
    }
}
