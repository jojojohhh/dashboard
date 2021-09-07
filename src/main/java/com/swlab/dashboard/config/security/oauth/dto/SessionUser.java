package com.swlab.dashboard.config.security.oauth.dto;

import com.swlab.dashboard.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SessionUser {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
