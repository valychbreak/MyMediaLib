package com.valychbreak.mymedialib.services.media.movie;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.exception.ExternalAPIException;
import com.valychbreak.mymedialib.services.media.tvshow.TmdbTvShowSearchService;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import com.valychbreak.mymedialib.services.utils.SearchResultFactory;
import com.valychbreak.mymedialib.services.utils.TmdbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TmdbMovieSearchService implements MovieSearchService {
    private static Logger LOG = LoggerFactory.getLogger(TmdbTvShowSearchService.class);

    private Tmdb tmdb;
    private TmdbService tmdbService;

    public TmdbMovieSearchService(Tmdb tmdb, TmdbService tmdbService) {
        this.tmdb = tmdb;
        this.tmdbService = tmdbService;
    }

    @Override
    public SearchResult<MediaFullDetails> search(SearchParams searchParams) throws ExternalAPIException {

        MovieResultsPage movieResults = searchMedia(searchParams, tmdb);

        List<MediaFullDetails> mediaSearchResults = new ArrayList<>();
        for (BaseMovie result : movieResults.results) {
            try {
                MediaFullDetails media = tmdbService.getMovieDetails(result);
                mediaSearchResults.add(media);
            } catch (ExternalAPIException | IOException e) {
                LOG.warn("Failed to load details for movie [name: {}, id: {}]. Ignoring in the search result.", result.title, result.id, e);
            }
        }

        SearchResultFactory searchResultFactory = new SearchResultFactory();
        return searchResultFactory.create(searchParams.getPage(),  movieResults.total_pages,  movieResults.total_results, mediaSearchResults);
    }

    private MovieResultsPage searchMedia(SearchParams searchParams, Tmdb tmdb) throws ExternalAPIException {
        try {
            Call<MovieResultsPage> call = tmdb.searchService().movie(searchParams.getQuery(), searchParams.getPage(), null, null, null, null, null);
            return call.execute().body();
        } catch (IOException e) {
            throw new ExternalAPIException("Failed to search for media. " + searchParams, e);
        }
    }
}
