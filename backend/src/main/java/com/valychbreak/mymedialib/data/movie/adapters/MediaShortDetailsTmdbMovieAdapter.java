package com.valychbreak.mymedialib.data.movie.adapters;

import com.uwetrottmann.tmdb2.entities.Movie;
import com.valychbreak.mymedialib.data.movie.impl.MediaShortDetailsImpl;

/**
 * Created by valych on 6/4/17.
 */
public class MediaShortDetailsTmdbMovieAdapter extends MediaShortDetailsImpl {
    private Movie movie;

    public MediaShortDetailsTmdbMovieAdapter(Movie movie) {
        super(movie.imdb_id, movie.title);
        this.movie = movie;
    }
}
