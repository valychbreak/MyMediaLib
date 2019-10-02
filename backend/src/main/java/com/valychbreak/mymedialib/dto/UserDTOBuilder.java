package com.valychbreak.mymedialib.dto;

import com.valychbreak.mymedialib.entity.User;

public class UserDTOBuilder {
    private Long id;
    private String username;
    private String name;
    private String email;
    private Long roleId;

    private UserDTOBuilder() {

    }

    public static UserDTOBuilder aUserDtoBuilderFromUser(User user) {
        return new UserDTOBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setRoleId(user.getRole().getId());
    }

    public UserDTOBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserDTOBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserDTOBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserDTOBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDTOBuilder setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public UserDTO build() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setUsername(username);
        userDTO.setEmail(email);
        userDTO.setName(name);
        userDTO.setRoleId(roleId);
        return userDTO;
    }
}