package com.valychbreak.mymedialib.dto.movie;

import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.Media;
import com.uwetrottmann.tmdb2.enumerations.MediaType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicMediaDTOTest {

    @Test
    public void posterImageIsEmptyWhenMoviePosterIsNull() throws Exception {
        Media tmdbMedia = new Media();
        BaseMovie baseMovie = new BaseMovie();
        baseMovie.poster_path = null;

        tmdbMedia.media_type = MediaType.MOVIE;
        tmdbMedia.movie = baseMovie;

        BasicMediaDTO basicMediaDTO = new BasicMediaDTO(tmdbMedia);
        assertThat(basicMediaDTO.getPosterImage()).isEqualTo("");
    }

    @Test
    public void posterImageIsEmptyWhenTvShowPosterIsNull() throws Exception {
        Media tmdbMedia = new Media();
        BaseTvShow baseTvShow = new BaseTvShow();
        tmdbMedia.tvShow = baseTvShow;

        tmdbMedia.media_type = MediaType.TV;
        baseTvShow.poster_path = null;

        BasicMediaDTO basicMediaDTO = new BasicMediaDTO(tmdbMedia);
        assertThat(basicMediaDTO.getPosterImage()).isEqualTo("");
    }
}