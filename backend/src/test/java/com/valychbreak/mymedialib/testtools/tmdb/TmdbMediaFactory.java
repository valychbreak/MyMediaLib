package com.valychbreak.mymedialib.testtools.tmdb;

import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.BasePerson;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.Media;
import com.uwetrottmann.tmdb2.enumerations.MediaType;

public class TmdbMediaFactory {

    public Media createMedia(BaseTvShow tvShow) {
        Media media = new Media();
        media.tvShow = tvShow;
        media.media_type = MediaType.TV;

        return media;
    }

    public Media createMedia(BaseMovie movie) {
        Media media = new Media();
        media.movie = movie;
        media.media_type = MediaType.MOVIE;

        return media;
    }

    public Media createMedia(BasePerson person) {
        Media media = new Media();
        media.person = person;
        media.media_type = MediaType.PERSON;

        return media;
    }
}
