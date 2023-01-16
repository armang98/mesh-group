package com.mesh.group.test.request;

import com.mesh.group.test.model.User;

import java.util.List;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    private User user;
    private String email;

    public CurrentUser(User user, String email) {
        super(email, user.getPassword(), List.of());
        this.user = user;
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }
}