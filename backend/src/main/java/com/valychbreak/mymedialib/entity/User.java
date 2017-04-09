package com.valychbreak.mymedialib.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.valychbreak.mymedialib.tools.gson.JsonExclude;

/**
 * Created by valych on 2/25/17.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_role_id", nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    @JsonExclude
    private List<UserMedia> favourites = new ArrayList<>();


    protected User() {
    }

    public User(String username, String name, String email) {
        this(null, username, name, email);
    }

    public User(Long id, String username, String name, String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<UserMedia> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<UserMedia> favourites) {
        this.favourites = favourites;
    }

    @Override
    public String toString() {
        return String.format("User[id='%d', username='%s']", id, username);
    }
}