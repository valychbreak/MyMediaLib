package com.valychbreak.mymedialib.entity;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.valychbreak.mymedialib.data.MediaFullDetails;
import com.valychbreak.mymedialib.data.MediaShortDetails;
import com.valychbreak.mymedialib.services.OmdbVideoProvider;
import com.valychbreak.mymedialib.tools.adapters.MediaFullDetailsAdapter;
import com.valychbreak.mymedialib.tools.adapters.MediaShortDetailsAdapter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by Valeriy on 3/18/2017.
 */
/*@Entity
@Table(name = "media")*/
public class Media {
    private Long id;
    private String imdbId;
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
