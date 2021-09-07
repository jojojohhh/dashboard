package com.swlab.dashboard.dto;

import com.swlab.dashboard.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UserDto {

    private String email;
    private String password;
    private String name;
    private String phoneNo;
    private String picture;

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .build();
    }

    @Builder
    public UserDto(String email, String password, String name, String phoneNo, String picture) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNo = phoneNo;
        this.picture = picture;
    }
}
