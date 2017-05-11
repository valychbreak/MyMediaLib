package com.valychbreak.mymedialib.entity.media;

import javax.persistence.*;
import java.util.List;

/**
 * Created by valych on 5/9/17.
 */
public class MediaCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_media_catalog")
    private MediaCatalog parentMediaCatalog;

    @OneToMany(mappedBy = "parentMediaCatalog")
    private List<MediaCatalog> subMediaCatalogs;

    @ManyToMany
    private List<UserMedia> userMediaList;

    protected MediaCatalog() { }

    public MediaCatalog(String name) {
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

    public MediaCatalog getParentMediaCatalog() {
        return parentMediaCatalog;
    }

    public void setParentMediaCatalog(MediaCatalog parentMediaCatalog) {
        this.parentMediaCatalog = parentMediaCatalog;
    }

    public List<MediaCatalog> getSubMediaCatalogs() {
        return subMediaCatalogs;
    }

    public void setSubMediaCatalogs(List<MediaCatalog> subMediaCatalogs) {
        this.subMediaCatalogs = subMediaCatalogs;
    }

    public List<UserMedia> getUserMediaList() {
        return userMediaList;
    }

    public void setUserMediaList(List<UserMedia> userMediaList) {
        this.userMediaList = userMediaList;
    }
}
