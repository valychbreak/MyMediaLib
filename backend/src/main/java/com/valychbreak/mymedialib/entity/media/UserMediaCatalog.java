package com.valychbreak.mymedialib.entity.media;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 5/9/17.
 */
@Entity
@Table(name = "user_media_catalog")
public class UserMediaCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_media_catalog")
    private UserMediaCatalog parentUserMediaCatalog;

    @OneToMany(mappedBy = "parentUserMediaCatalog")
    private List<UserMediaCatalog> subUserMediaCatalogs = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_media_catalog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_media_id")
    )
    private List<UserMedia> userMediaList = new ArrayList<>();

    protected UserMediaCatalog() { }

    public UserMediaCatalog(String name) {
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

    public UserMediaCatalog getParentUserMediaCatalog() {
        return parentUserMediaCatalog;
    }

    public void setParentUserMediaCatalog(UserMediaCatalog parentUserMediaCatalog) {
        this.parentUserMediaCatalog = parentUserMediaCatalog;
    }

    public List<UserMediaCatalog> getSubUserMediaCatalogs() {
        return subUserMediaCatalogs;
    }

    public void setSubUserMediaCatalogs(List<UserMediaCatalog> subUserMediaCatalogs) {
        this.subUserMediaCatalogs = subUserMediaCatalogs;
    }

    public List<UserMedia> getUserMediaList() {
        return userMediaList;
    }

    public void setUserMediaList(List<UserMedia> userMediaList) {
        this.userMediaList = userMediaList;
    }
}
