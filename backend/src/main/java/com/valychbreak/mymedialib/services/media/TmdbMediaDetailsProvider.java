package com.valychbreak.mymedialib.services.media;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.FindResults;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.TvShow;
import com.uwetrottmann.tmdb2.enumerations.ExternalSource;
import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsTmdbMovieAdapter;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.utils.TmdbUtils;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;

@Service
public class TmdbMediaDetailsProvider implements MediaDetailsProvider {
    private Tmdb tmdb;

    public TmdbMediaDetailsProvider(Tmdb tmdb) {
        this.tmdb = tmdb;
    }

    @Override
    public MediaShortDetails getShortDetails(Media media) {
        return null;
    }

    @Override
    public MediaFullDetails getDetails(Media media) throws IOException {
        String imdbId = media.getImdbId();

        Call<FindResults> call = tmdb.findService().find(imdbId, ExternalSource.IMDB_ID, null);
        FindResults body = call.execute().body();

        Movie movie = body.movie_results.isEmpty() ? null : body.movie_results.get(0);
        TvShow tvShow = body.tv_results.isEmpty()? null : TmdbUtils.requestDetailedTmdbTvShow(tmdb, body.tv_results.get(0));

        if (movie != null) {
            return new MediaFullDetailsTmdbMovieAdapter(movie);
        } else {
            return new MediaFullDetailsTmdbMovieAdapter(tvShow);
        }
    }
}
