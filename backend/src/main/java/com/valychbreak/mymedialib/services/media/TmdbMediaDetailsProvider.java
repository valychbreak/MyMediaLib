package com.valychbreak.mymedialib.services.media;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.FindResults;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.enumerations.ExternalSource;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.services.utils.TmdbService;
import org.springframework.stereotype.Component;
import retrofit2.Call;

import java.io.IOException;

@Component
public class TmdbMediaDetailsProvider implements MediaDetailsProvider {
    private Tmdb tmdb;
    private TmdbService tmdbService;

    public TmdbMediaDetailsProvider(Tmdb tmdb, TmdbService tmdbService) {
        this.tmdb = tmdb;
        this.tmdbService = tmdbService;
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

        if (movie != null) {
            return tmdbService.getMovieDetails(movie);
        } else {
            return tmdbService.getTvShowDetails(body.tv_results.get(0));
        }
    }
}
