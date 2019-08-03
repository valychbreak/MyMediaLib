package com.valychbreak.mymedialib.services;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.FindResults;
import com.uwetrottmann.tmdb2.entities.Media;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.TvShow;
import com.uwetrottmann.tmdb2.enumerations.ExternalSource;
import com.uwetrottmann.tmdb2.enumerations.MediaType;
import com.valychbreak.mymedialib.utils.TmdbUtils;
import org.springframework.stereotype.Component;
import retrofit2.Call;

import java.io.IOException;

/**
 * Created by valych on 6/2/17.
 */
@Component
public class TmdbMediaProvider {
    private Tmdb tmdb;

    public TmdbMediaProvider(Tmdb tmdb) {
        this.tmdb = tmdb;
    }

    public Movie getMovieBy(String imdbId) throws IOException {
        Call<FindResults> call = tmdb.findService().find(imdbId, ExternalSource.IMDB_ID, null);
        FindResults body = call.execute().body();
        Movie movie = body.movie_results.get(0);
        movie.imdb_id = imdbId;

        return movie;
    }

    public Media getMediaBy(String imdbId) throws IOException {
        Call<FindResults> call = tmdb.findService().find(imdbId, ExternalSource.IMDB_ID, null);
        FindResults body = call.execute().body();

        Movie movie = body.movie_results.isEmpty() ? null : body.movie_results.get(0);
        TvShow tvShow = body.tv_results.isEmpty()? null : TmdbUtils.requestDetailedTmdbTvShow(tmdb, body.tv_results.get(0));

        Media media = new Media();
        media.tvShow = tvShow;
        media.movie = movie;
        if (movie != null) {
            movie.imdb_id = imdbId;
            media.media_type = MediaType.MOVIE;
        }

        if (tvShow != null) {
            tvShow.external_ids.imdb_id = imdbId;
            media.media_type = MediaType.TV;
        }
        return media;
    }
}
