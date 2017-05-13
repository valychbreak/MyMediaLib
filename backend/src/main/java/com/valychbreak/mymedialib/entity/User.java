package com.valychbreak.mymedialib.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.tools.gson.JsonExclude;
import org.hibernate.validator.constraints.Email;

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

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_role_id", nullable = false)
    private Role role;

    @JsonExclude
    @ManyToOne
    @JoinColumn(name = "root_user_media_catalog", nullable = false)
    private UserMediaCatalog rootUserMediaCatalog;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonExclude
    private List<UserMedia> allFavorites = new ArrayList<>();


    protected User() {
    }

    public User(String username, String password, String name, String email, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<UserMedia> getAllFavorites() {
        return allFavorites;
    }

    public void setAllFavorites(List<UserMedia> allFavorites) {
        this.allFavorites = allFavorites;
    }

    public UserMediaCatalog getRootUserMediaCatalog() {
        return rootUserMediaCatalog;
    }

    public void setRootUserMediaCatalog(UserMediaCatalog rootUserMediaCatalog) {
        this.rootUserMediaCatalog = rootUserMediaCatalog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("User[id='%d', username='%s']", id, username);
    }
}
