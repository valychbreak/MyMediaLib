package com.valychbreak.mymedialib.testtools;

import com.uwetrottmann.tmdb2.entities.Movie;

public class TmdbMovieBuilder {
    private Integer id;
    private String title;
    private String mediaType;
    private String backdropPath;
    private String imdbId;
    private Double voteAverage;
    private Integer voteCount;

    public TmdbMovieBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public TmdbMovieBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public TmdbMovieBuilder setMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public TmdbMovieBuilder setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public TmdbMovieBuilder setImdbId(String imdbId) {
        this.imdbId = imdbId;
        return this;
    }

    public TmdbMovieBuilder setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    public TmdbMovieBuilder setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    public Movie build() {
        Movie movie = new Movie();
        movie.id = id;
        movie.title = title;
        movie.media_type = mediaType;
        movie.backdrop_path = backdropPath;
        movie.imdb_id = imdbId;
        movie.vote_count = voteCount;
        movie.vote_average = voteAverage;
        return movie;
    }
}
