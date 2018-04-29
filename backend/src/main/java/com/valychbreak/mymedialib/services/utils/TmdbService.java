package com.valychbreak.mymedialib.services.utils;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.*;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsTmdbMovieAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.Optional;

@Service
public class TmdbService {
    private Tmdb tmdb;

    public TmdbService(Tmdb tmdb) {
        this.tmdb = tmdb;
    }

    public Optional<MediaFullDetails> getMediaDetails(Media tmdbMedia) throws IOException {
        MediaFullDetails media = null;

        switch (tmdbMedia.media_type) {
            case TV:
                media = getTvShowDetails(tmdbMedia.tvShow);
                break;
            case MOVIE:
                media = getMovieDetails(tmdbMedia.movie);
                break;
        }

        return Optional.ofNullable(media);
    }

    public MediaFullDetails getTvShowDetails(BaseTvShow result) throws IOException {
        TvShow tvShow = requestDetailedTmdbTvShow(tmdb, result);
        MediaFullDetails media = new MediaFullDetailsTmdbMovieAdapter(tvShow);
        return media;
    }

    public MediaFullDetails getMovieDetails(BaseMovie result) throws IOException {
        MediaFullDetails media = null;
        Movie movie = requestDetailedTmdbMovie(tmdb, result);
        //FIXME: get rid of imdbId
        if(movie != null && StringUtils.isNotBlank(movie.imdb_id) && movie.backdrop_path != null) {
            media = new MediaFullDetailsTmdbMovieAdapter(movie);
        }

        return media;
    }

    private Movie requestDetailedTmdbMovie(Tmdb tmdb, BaseMovie result) throws IOException {
        Call<Movie> summary = tmdb.moviesService().summary(result.id, null, new AppendToResponse(AppendToResponseItem.EXTERNAL_IDS));
        return summary.execute().body();
    }

    private TvShow requestDetailedTmdbTvShow(Tmdb tmdb, BaseTvShow result) throws IOException {
        Call<TvShow> summary = tmdb.tvService().tv(result.id, null, new AppendToResponse(AppendToResponseItem.EXTERNAL_IDS));
        return summary.execute().body();
    }
}
