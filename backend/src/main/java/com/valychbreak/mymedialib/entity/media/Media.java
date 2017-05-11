package com.valychbreak.mymedialib.entity.media;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.services.OmdbVideoProvider;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsAdapter;
import com.valychbreak.mymedialib.data.movie.adapters.MediaShortDetailsAdapter;

import javax.persistence.*;

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

    @Transient
    public MediaShortDetails getShortDetails() throws OMDBException {
        OmdbVideoFull omdbVideo = new OmdbVideoProvider().getOmdbVideo(imdbId);
        return new MediaShortDetailsAdapter(omdbVideo);
    }

    @Transient
    public MediaFullDetails getDetails() throws OMDBException {
        OmdbVideoFull omdbVideo = new OmdbVideoProvider().getOmdbVideo(imdbId);
        return new MediaFullDetailsAdapter(omdbVideo);
    }
}