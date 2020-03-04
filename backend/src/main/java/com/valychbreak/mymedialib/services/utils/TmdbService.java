package com.valychbreak.mymedialib.services.utils;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.*;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.uwetrottmann.tmdb2.exceptions.TmdbNotFoundException;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsTmdbMovieAdapter;
import com.valychbreak.mymedialib.exception.ExternalAPIException;
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

    public Optional<MediaFullDetails> getMediaDetails(Media tmdbMedia) throws IOException, ExternalAPIException {
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

    public MediaFullDetails getTvShowDetails(BaseTvShow result) throws IOException, ExternalAPIException {
        TvShow tvShow = requestDetailedTmdbTvShow(result);
        return new MediaFullDetailsTmdbMovieAdapter(tvShow);
    }

    public MediaFullDetails getMovieDetails(BaseMovie result) throws ExternalAPIException, IOException {
        MediaFullDetails media;
        Movie movie = requestDetailedTmdbMovie(result);
        //FIXME: get rid of imdbId
        if(movie != null && StringUtils.isNotBlank(movie.imdb_id) && movie.backdrop_path != null) {
            media = new MediaFullDetailsTmdbMovieAdapter(movie);
            return media;
        }

        throw new ExternalAPIException("Failed to load movie.", new IllegalArgumentException("Whether imdb id was not provided or backdrop path"));
    }

    public Movie requestDetailedTmdbMovie(BaseMovie result) throws ExternalAPIException, IOException {
        Movie movie;
        try {
            movie = requestTmdbMovie(tmdb, result);
        } catch (TmdbNotFoundException e) {
            String message = String.format("Failed to request details for movie [title: %s, id: %s]", result.title, result.id);
            throw new ExternalAPIException(message, e);
        } catch(IllegalStateException | IOException e) {
            LOGGER.warn("TMDB API call failed. Retrying...", e);
            movie = requestTmdbMovie(tmdb, result);
        }

        return movie;
    }

    public TvShow requestDetailedTmdbTvShow(BaseTvShow result) throws IOException, ExternalAPIException {
        TvShow tvShow;
        try {
            tvShow = requestTmdbTvShow(tmdb, result);
        } catch (TmdbNotFoundException e) {
            String message = String.format("Failed to request details for tv show [name: %s, id: %s]", result.name, result.id);
            throw new ExternalAPIException(message, e);
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
