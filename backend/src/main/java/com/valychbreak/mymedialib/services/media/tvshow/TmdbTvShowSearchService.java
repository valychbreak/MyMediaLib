package com.valychbreak.mymedialib.services.media.tvshow;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.TvShowResultsPage;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import com.valychbreak.mymedialib.services.utils.SearchResultFactory;
import com.valychbreak.mymedialib.services.utils.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TmdbTvShowSearchService implements TvShowSearchService {

    private Tmdb tmdb;
    private TmdbService tmdbService;

    @Autowired
    public TmdbTvShowSearchService(Tmdb tmdb, TmdbService tmdbService) {
        this.tmdb = tmdb;
        this.tmdbService = tmdbService;
    }

    @Override
    public SearchResult<MediaFullDetails> search(SearchParams searchParams) throws IOException {
        TvShowResultsPage movieResults = searchMedia(searchParams, tmdb);

        List<MediaFullDetails> mediaSearchResults = new ArrayList<>();
        for (BaseTvShow result : movieResults.results) {
            MediaFullDetails media = tmdbService.getTvShowDetails(result);

            if(media != null) {
                mediaSearchResults.add(media);
            }

        }

        SearchResultFactory searchResultFactory = new SearchResultFactory();
        return searchResultFactory.create(searchParams.getPage(),  movieResults.total_pages,  movieResults.total_results, mediaSearchResults);
    }

    private TvShowResultsPage searchMedia(SearchParams searchParams, Tmdb tmdb) throws IOException {
        Call<TvShowResultsPage> call = tmdb.searchService().tv(searchParams.getQuery(), searchParams.getPage(), null, null, null);
        return call.execute().body();
    }
}
