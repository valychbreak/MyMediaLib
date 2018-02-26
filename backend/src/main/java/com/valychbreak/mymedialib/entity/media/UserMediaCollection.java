package com.valychbreak.mymedialib.entity.media;

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

    @ManyToOne
    @JoinColumn(name = "parent_media_collection")
    private UserMediaCollection parentUserMediaCollection;

    @OneToMany(mappedBy = "parentUserMediaCollection")
    private List<UserMediaCollection> subUserMediaCollections = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_media_collection_id"),
            inverseJoinColumns = @JoinColumn(name = "user_media_id")
    )
    private List<UserMedia> userMediaList = new ArrayList<>();

    protected UserMediaCollection() { }

    public UserMediaCollection(String name) {
        this.name = name;
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

    public UserMediaCollection getParentUserMediaCollection() {
        return parentUserMediaCollection;
    }

    public void setParentUserMediaCollection(UserMediaCollection parentUserMediaCollection) {
        this.parentUserMediaCollection = parentUserMediaCollection;
    }

    public List<UserMediaCollection> getSubUserMediaCollections() {
        return subUserMediaCollections;
    }

    public void setSubUserMediaCollections(List<UserMediaCollection> subUserMediaCollections) {
        this.subUserMediaCollections = subUserMediaCollections;
    }

    public List<UserMedia> getUserMediaList() {
        return userMediaList;
    }

    public void setUserMediaList(List<UserMedia> userMediaList) {
        this.userMediaList = userMediaList;
    }
}
