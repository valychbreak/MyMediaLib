package com.valychbreak.mymedialib.entity.media;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omertron.omdbapi.OMDBException;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaShortDetailsTmdbMovieAdapter;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.services.TmdbMediaProvider;
import com.valychbreak.mymedialib.utils.TmdbUtils;

import javax.persistence.*;
import java.io.IOException;
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

    @JsonIgnore
    @Transient
    @Deprecated
    public MediaShortDetails getShortDetails() throws OMDBException, IOException {
        Movie movie = new TmdbMediaProvider().getMovieBy(imdbId);
        MediaShortDetails media = new MediaShortDetailsTmdbMovieAdapter(movie);
        return media;
    }

    @JsonIgnore
    @Transient
    @Deprecated
    public MediaFullDetails getDetails() throws OMDBException, IOException {
        //OmdbVideoFull omdbVideo = new OmdbVideoProvider().getOmdbVideo(imdbId);
        com.uwetrottmann.tmdb2.entities.Media mediaBy = new TmdbMediaProvider().getMediaBy(imdbId);
        MediaFullDetailsImpl media = (MediaFullDetailsImpl) TmdbUtils.getMediaFullDetailsFromTmdbMedia(APIController.TMDB_INSTANCE, mediaBy);
        return media;
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
