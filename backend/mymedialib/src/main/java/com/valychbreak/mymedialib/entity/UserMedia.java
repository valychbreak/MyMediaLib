package com.valychbreak.mymedialib.entity;

import java.util.Date;

/**
 * Created by Valeriy on 3/18/2017.
 */
public class UserMedia {
    private Long id;

    private Date addingDate;

    private User user;
    private Media media;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Date addingDate) {
        this.addingDate = addingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
