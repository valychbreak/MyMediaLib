package com.valychbreak.mymedialib.dto;

import com.valychbreak.mymedialib.entity.User;

public class UserDTO {
    private Long id;
    private String username;
    private String name;
    private String email;
    private Long roleId;

    public UserDTO (User user) {
        id = user.getId();
        username = user.getUsername();
        name = user.getName();
        email = user.getEmail();
        roleId = user.getRole().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
