package com.valychbreak.mymedialib.data.movie.adapters;

import com.uwetrottmann.tmdb2.entities.Genre;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.TvShow;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;

import java.util.List;

/**
 * Created by valych on 6/1/17.
 */
public class MediaFullDetailsTmdbMovieAdapter extends MediaFullDetailsImpl {
    public static final String TMDB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342/";
    private Movie movie;

    public MediaFullDetailsTmdbMovieAdapter(TvShow tvShow) {
        convert(tvShow);
    }

    public MediaFullDetailsTmdbMovieAdapter(Movie movie) {
        this.movie = movie;
        convert(movie);
    }

    private void convert(Movie movie) {
        imdbId = movie.imdb_id;
        title = movie.title;
        genre = getGenre(movie.genres);
        duration = movie.runtime != null ? movie.runtime.toString() : "116";
        description = movie.overview;
        imagePath = TMDB_IMAGE_BASE_URL + movie.backdrop_path;
        stars = movie.vote_average.toString();
        reviews = movie.vote_count.toString();
        type = "Movie";
    }

    private void convert(TvShow tvShow) {
        imdbId = tvShow.external_ids.imdb_id;
        title = tvShow.name;
        genre = getGenre(tvShow.genres);
        duration = tvShow.episode_run_time != null && !tvShow.episode_run_time.isEmpty() ?
                tvShow.episode_run_time.get(0).toString() : "116";
        description = tvShow.overview;
        imagePath = TMDB_IMAGE_BASE_URL + tvShow.backdrop_path;
        stars = tvShow.rating != null ? tvShow.rating.toString() : "0";
        reviews = tvShow.vote_count.toString();
        type = "TV Show";
    }

    private String getGenre(List<Genre> genres) {
        return genres != null && genres.size() > 0 ? genres.get(0).name : "Adventure";
    }
}
