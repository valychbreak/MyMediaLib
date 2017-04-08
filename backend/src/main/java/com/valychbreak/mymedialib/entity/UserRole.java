package com.valychbreak.mymedialib.entity;

import com.valychbreak.mymedialib.tools.gson.JsonExclude;

import javax.persistence.*;
import java.util.List;

/**
 * Created by valych on 4/1/17.
 */
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role")
    private String role;

    @JsonExclude
    @OneToMany(mappedBy = "role")
    private List<User> users;

    protected UserRole() {}

    public UserRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
