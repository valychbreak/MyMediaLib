package com.valychbreak.mymedialib.testtools.tmdb;

import com.uwetrottmann.tmdb2.entities.BaseTvShow;

import java.util.Date;
import java.util.List;

public class BaseTvShowBuilder {
    private Integer id;
    private String original_name;
    private String original_language;
    private String overview;
    private String name;
    private List<String> origin_country;
    private List<Integer> genre_ids;
    private Date first_air_date;
    private String backdrop_path;
    private String poster_path;
    private Double popularity;
    private Double vote_average;
    private Integer vote_count;
    private String media_type;

    public static BaseTvShowBuilder aBaseTvShowBuilder() {
        return new BaseTvShowBuilder();
    }

    public BaseTvShowBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public BaseTvShowBuilder setOriginal_name(String original_name) {
        this.original_name = original_name;
        return this;
    }

    public BaseTvShowBuilder setOriginal_language(String original_language) {
        this.original_language = original_language;
        return this;
    }

    public BaseTvShowBuilder setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public BaseTvShowBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BaseTvShowBuilder setOrigin_country(List<String> origin_country) {
        this.origin_country = origin_country;
        return this;
    }

    public BaseTvShowBuilder setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
        return this;
    }

    public BaseTvShowBuilder setFirst_air_date(Date first_air_date) {
        this.first_air_date = first_air_date;
        return this;
    }

    public BaseTvShowBuilder setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
        return this;
    }

    public BaseTvShowBuilder setPoster_path(String poster_path) {
        this.poster_path = poster_path;
        return this;
    }

    public BaseTvShowBuilder setPopularity(Double popularity) {
        this.popularity = popularity;
        return this;
    }

    public BaseTvShowBuilder setVote_average(Double vote_average) {
        this.vote_average = vote_average;
        return this;
    }

    public BaseTvShowBuilder setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
        return this;
    }

    public BaseTvShowBuilder setMedia_type(String media_type) {
        this.media_type = media_type;
        return this;
    }

    public BaseTvShow createBaseTvShow() {
        BaseTvShow baseTvShow = new BaseTvShow();
        baseTvShow.id = id;
        baseTvShow.original_name = original_name;
        baseTvShow.overview = overview;
        baseTvShow.name = name;
        baseTvShow.origin_country = origin_country;
        baseTvShow.genre_ids = genre_ids;
        baseTvShow.first_air_date = first_air_date;
        baseTvShow.backdrop_path = backdrop_path;
        baseTvShow.poster_path = poster_path;
        baseTvShow.popularity = popularity;
        baseTvShow.vote_average = vote_average;
        baseTvShow.vote_count = vote_count;
        baseTvShow.media_type = media_type;
        return baseTvShow;
    }
}