package com.example.newsapi.entity.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, JOURNALIST, SUBSCRIBER;

    @Override
    public String getAuthority() {
        return name();
    }
}
