package com.valychbreak.mymedialib.dto.movie;

import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.Media;

import java.util.Date;

import static com.valychbreak.mymedialib.utils.TmdbUtils.TMDB_IMAGE_BASE_URL;

public class BasicMediaDTO {
    private String name;
    private String posterImage;
    private String overview;
    private Date releaseDate;

    public BasicMediaDTO(Media media) {
        switch (media.media_type) {
            case MOVIE:
                init(media.movie);
                break;
            case TV:
                init(media.tvShow);
                break;
        }
    }

    private void init(BaseMovie baseMovie) {
        name = baseMovie.title;
        posterImage = TMDB_IMAGE_BASE_URL + baseMovie.poster_path;
        overview = baseMovie.overview;
        releaseDate = baseMovie.release_date;
    }

    private void init(BaseTvShow baseTvShow) {
        name = baseTvShow.name;
        posterImage = TMDB_IMAGE_BASE_URL + baseTvShow.poster_path;
        overview = baseTvShow.overview;
        releaseDate = baseTvShow.first_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
