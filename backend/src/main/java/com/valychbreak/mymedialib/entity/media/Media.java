package com.valychbreak.mymedialib.entity.media;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Valeriy on 3/18/2017.
 */
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "imdbId", nullable = false, unique = true)
    private String imdbId;

    @Column(name = "title")
    private String title;

    protected Media() { }

    public Media(String imdbId, String title) {
        this.imdbId = imdbId;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return Objects.equals(id, media.id) &&
                Objects.equals(imdbId, media.imdbId) &&
                Objects.equals(title, media.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, imdbId, title);
    }
}
