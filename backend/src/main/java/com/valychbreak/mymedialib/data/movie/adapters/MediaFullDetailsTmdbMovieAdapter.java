package com.valychbreak.mymedialib.data.movie.adapters;

import com.uwetrottmann.tmdb2.entities.Genre;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.TvShow;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;

import java.util.List;

import static com.valychbreak.mymedialib.utils.TmdbUtils.TMDB_IMAGE_BASE_URL;
import static com.valychbreak.mymedialib.utils.TmdbUtils.getPosterImageLink;

/**
 * Created by valych on 6/1/17.
 */
public class MediaFullDetailsTmdbMovieAdapter extends MediaFullDetailsImpl {

    public MediaFullDetailsTmdbMovieAdapter(TvShow tvShow) {
        convert(tvShow);
    }

    public MediaFullDetailsTmdbMovieAdapter(Movie movie) {
        convert(movie);
    }

    private void convert(Movie movie) {
        imdbId = movie.imdb_id;
        title = movie.title;
        genre = getGenre(movie.genres);
        duration = movie.runtime != null ? movie.runtime.toString() : "116";
        description = movie.overview;
        imagePath = getPosterImageLink(movie.poster_path);
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
        imagePath = getPosterImageLink(tvShow.poster_path);
        stars = tvShow.rating != null ? tvShow.rating.toString() : "0";
        reviews = tvShow.vote_count.toString();
        type = "TV Show";
    }

    private String getGenre(List<Genre> genres) {
        return genres != null && genres.size() > 0 ? genres.get(0).name : "Adventure";
    }
}
