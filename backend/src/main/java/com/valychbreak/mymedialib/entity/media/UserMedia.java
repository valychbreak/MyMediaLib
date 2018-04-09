package com.valychbreak.mymedialib.entity.media;

import com.valychbreak.mymedialib.entity.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Valeriy on 3/18/2017.
 */
@Entity
@Table(name = "user_media")
public class UserMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "adding_date")
    private Date addingDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "media_id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMedia userMedia = (UserMedia) o;
        return Objects.equals(id, userMedia.id) &&
                Objects.equals(user, userMedia.user) &&
                Objects.equals(media, userMedia.media);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, media);
    }
}
