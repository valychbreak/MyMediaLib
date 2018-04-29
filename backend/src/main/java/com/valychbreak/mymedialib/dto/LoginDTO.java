package com.valychbreak.mymedialib.dto;

/**
 * Created by valych on 2/26/17.
 */
public class LoginDTO {
    private String username;
    private String password;

    public LoginDTO() { }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
