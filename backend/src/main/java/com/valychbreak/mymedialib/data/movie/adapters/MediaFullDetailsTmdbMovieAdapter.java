package com.valychbreak.mymedialib.data.movie.adapters;

import com.uwetrottmann.tmdb2.entities.Movie;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;

/**
 * Created by valych on 6/1/17.
 */
public class MediaFullDetailsTmdbMovieAdapter extends MediaFullDetailsImpl {
    public MediaFullDetailsTmdbMovieAdapter(Movie result) {
        imdbId = result.imdb_id;
        title = result.title;
        genre = result.genres != null && result.genres.size() > 0 ? result.genres.get(0).name : "Adventure";
        duration = result.runtime != null ? result.runtime.toString() : "116";
        description = result.overview;
        imagePath = "http://image.tmdb.org/t/p/w342/" + result.backdrop_path;
        stars = result.vote_average.toString();
        reviews = result.vote_count.toString();
        type = "Movie";
    }
}
