package com.valychbreak.mymedialib.dto.newstructure;

import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.Media;

import java.util.Date;

import static com.valychbreak.mymedialib.utils.TmdbUtils.TMDB_IMAGE_BASE_URL;

public class BasicMediaDTO {
    private Integer id;
    private String title;
    private String posterImage;
    private String overview;
    private Date releaseDate;
    private String mediaType;
    private String[] genres;
    private Boolean adult;

    private String backdropImage;
    //public List<Genre> genres;
    //private List<Integer> genreIds;
    /*private String original_title;
    private String original_language;*/
    private Double popularity;
    private Double voteAverage;
    private Integer voteCount;

    public BasicMediaDTO() { }

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
        title = baseMovie.title;
        posterImage = TMDB_IMAGE_BASE_URL + baseMovie.poster_path;
        overview = baseMovie.overview;
        releaseDate = baseMovie.release_date;
        mediaType = baseMovie.media_type;
    }

    private void init(BaseTvShow baseTvShow) {
        title = baseTvShow.name;
        posterImage = TMDB_IMAGE_BASE_URL + baseTvShow.poster_path;
        overview = baseTvShow.overview;
        releaseDate = baseTvShow.first_air_date;
        mediaType = baseTvShow.media_type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdropImage() {
        return backdropImage;
    }

    public void setBackdropImage(String backdropImage) {
        this.backdropImage = backdropImage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
