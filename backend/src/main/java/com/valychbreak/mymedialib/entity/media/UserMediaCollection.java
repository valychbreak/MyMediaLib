package com.valychbreak.mymedialib.entity.media;

import com.valychbreak.mymedialib.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 5/9/17.
 */
@Entity
@Table(name = "user_media_collection")
public class UserMediaCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_media_collection_id"),
            inverseJoinColumns = @JoinColumn(name = "user_media_id")
    )
    private List<UserMedia> userMediaList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User owner;

    protected UserMediaCollection() { }

    @Deprecated
    public UserMediaCollection(String name) {
        this.name = name;
    }

    public UserMediaCollection(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserMedia> getUserMediaList() {
        return userMediaList;
    }

    public void setUserMediaList(List<UserMedia> userMediaList) {
        this.userMediaList = userMediaList;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
