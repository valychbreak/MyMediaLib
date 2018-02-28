package com.valychbreak.mymedialib.services.media.movie;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.Media;
import com.uwetrottmann.tmdb2.entities.MediaResultsPage;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import com.valychbreak.mymedialib.services.utils.SearchResultFactory;
import com.valychbreak.mymedialib.utils.TmdbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultMovieSearchService implements MovieSearchService {
    private Tmdb tmdb;

    @Autowired
    public DefaultMovieSearchService(Tmdb tmdb) {
        this.tmdb = tmdb;
    }

    @Override
    public SearchResult<MediaFullDetails> search(SearchParams searchParams) throws IOException {

        MovieResultsPage movieResults = searchMedia(searchParams, tmdb);

        List<MediaFullDetails> mediaSearchResults = new ArrayList<>();
        for (BaseMovie result : movieResults.results) {
            // TODO: get rid of static methods - no way to create unit tests
            MediaFullDetails media = TmdbUtils.getMediaFullDetailsFromTmdbMovie(tmdb, result);

            if(media != null) {
                mediaSearchResults.add(media);
            }

        }

        SearchResultFactory searchResultFactory = new SearchResultFactory();
        return searchResultFactory.create(searchParams.getPage(),  movieResults.total_pages,  movieResults.total_results, mediaSearchResults);
    }

    private MovieResultsPage searchMedia(SearchParams searchParams, Tmdb tmdb) throws IOException {
        Call<MovieResultsPage> call = tmdb.searchService().movie(searchParams.getQuery(), searchParams.getPage(), null, null, null, null, null);
        return call.execute().body();
    }
}
