package com.valychbreak.mymedialib.services;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.FindResults;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.enumerations.ExternalSource;
import retrofit2.Call;

import java.io.IOException;

/**
 * Created by valych on 6/2/17.
 */
public class TmdbMovieProvider {
    public Movie getMovieBy(String imdbId) throws IOException {
        Tmdb tmdb = new Tmdb("01e924145da414b33cdc651619bb694b");
        Call<FindResults> call = tmdb.findService().find(imdbId, ExternalSource.IMDB_ID, null);
        FindResults body = call.execute().body();
        Movie movie = body.movie_results.get(0);
        movie.imdb_id = imdbId;

        return movie;
    }
}
