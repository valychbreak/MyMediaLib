package com.valychbreak.mymedialib.services.utils;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.*;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsTmdbMovieAdapter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.Optional;

@Service
public class TmdbService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TmdbService.class);

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
        TvShow tvShow = requestDetailedTmdbTvShow(result);
        MediaFullDetails media = new MediaFullDetailsTmdbMovieAdapter(tvShow);
        return media;
    }

    public MediaFullDetails getMovieDetails(BaseMovie result) throws IOException {
        MediaFullDetails media = null;
        Movie movie = requestDetailedTmdbMovie(result);
        //FIXME: get rid of imdbId
        if(movie != null && StringUtils.isNotBlank(movie.imdb_id) && movie.backdrop_path != null) {
            media = new MediaFullDetailsTmdbMovieAdapter(movie);
        }

        return media;
    }

    public Movie requestDetailedTmdbMovie(BaseMovie result) throws IOException {
        Movie movie = null;
        try {
            movie = requestTmdbMovie(tmdb, result);
        } catch(IllegalStateException | IOException e) {
            LOGGER.warn("TMDB API call failed. Retrying...", e);
            movie = requestTmdbMovie(tmdb, result);
        }

        return movie;
    }

    public TvShow requestDetailedTmdbTvShow(BaseTvShow result) throws IOException {
        TvShow tvShow;
        try {
            tvShow = requestTmdbTvShow(tmdb, result);
        } catch(IllegalStateException | IOException e) {
            LOGGER.warn("TMDB API call failed. Retrying...", e);
            tvShow = requestTmdbTvShow(tmdb, result);
        }

        return tvShow;
    }

    private Movie requestTmdbMovie(Tmdb tmdb, BaseMovie result) throws IOException {
        Call<Movie> summary = tmdb.moviesService().summary(result.id, null, new AppendToResponse(AppendToResponseItem.EXTERNAL_IDS));
        return summary.execute().body();
    }

    private TvShow requestTmdbTvShow(Tmdb tmdb, BaseTvShow result) throws IOException {
        Call<TvShow> summary = tmdb.tvService().tv(result.id, null, new AppendToResponse(AppendToResponseItem.EXTERNAL_IDS));
        return summary.execute().body();
    }
}
