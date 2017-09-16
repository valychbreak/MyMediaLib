package com.valychbreak.mymedialib.utils;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.*;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsTmdbMovieAdapter;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;

import java.io.IOException;

/**
 * Created by valych on 9/16/17.
 */
public class TmdbUtils {
    public static Movie requestDetailedTmdbTvShow(Tmdb tmdb, BaseMovie result) throws IOException {

        Call<Movie> summary = tmdb.moviesService().summary(result.id, null, new AppendToResponse(AppendToResponseItem.EXTERNAL_IDS));
        return summary.execute().body();
    }

    public static TvShow requestDetailedTmdbTvShow(Tmdb tmdb, BaseTvShow result) throws IOException {

        Call<TvShow> summary = tmdb.tvService().tv(result.id, null, new AppendToResponse(AppendToResponseItem.EXTERNAL_IDS));
        return summary.execute().body();
    }

    public static MediaFullDetails getMediaFullDetailsFromTmdbMedia(Tmdb tmdb, Media result) throws IOException {
        MediaFullDetails media = null;

        switch (result.media_type) {
            case TV:
                TvShow tvShow = requestDetailedTmdbTvShow(tmdb, result.tvShow);
                media = new MediaFullDetailsTmdbMovieAdapter(tvShow);
                break;
            case MOVIE:
                Movie movie = requestDetailedTmdbTvShow(tmdb, result.movie);
                //FIXME: get rid of imdbId
                if(movie != null && StringUtils.isNotBlank(movie.imdb_id) && movie.backdrop_path != null) {
                    media = new MediaFullDetailsTmdbMovieAdapter(movie);
                }
                break;
        }

        return media;
    }
}
