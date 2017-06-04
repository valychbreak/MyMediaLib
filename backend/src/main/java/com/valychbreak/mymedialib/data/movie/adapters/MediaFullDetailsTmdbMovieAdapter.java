package com.valychbreak.mymedialib.data.movie.adapters;

import com.uwetrottmann.tmdb2.entities.Movie;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;

/**
 * Created by valych on 6/1/17.
 */
public class MediaFullDetailsTmdbMovieAdapter extends MediaFullDetailsImpl {
    private Movie movie;

    public MediaFullDetailsTmdbMovieAdapter(Movie movie) {
        this.movie = movie;
        convert(movie);
    }

        private void convert(Movie movie) {
            imdbId = movie.imdb_id;
            title = movie.title;
            genre = movie.genres != null && movie.genres.size() > 0 ? movie.genres.get(0).name : "Adventure";
            duration = movie.runtime != null ? movie.runtime.toString() : "116";
            description = movie.overview;
            imagePath = "http://image.tmdb.org/t/p/w342/" + movie.backdrop_path;
            stars = movie.vote_average.toString();
            reviews = movie.vote_count.toString();
            type = "Movie";
        }
}
