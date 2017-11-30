package com.valychbreak.mymedialib.services.media;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.Media;
import com.uwetrottmann.tmdb2.entities.MediaResultsPage;
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
public class DefaultMediaSearchService implements MediaSearchService {

    private Tmdb tmdb;

    @Autowired
    public DefaultMediaSearchService(Tmdb tmdb) {
        this.tmdb = tmdb;
    }

    @Override
    public SearchResult<MediaFullDetails> search(SearchParams searchParams) throws IOException {
        MediaResultsPage movieResults = searchMedia(searchParams, tmdb);
        List<MediaFullDetails> mediaSearchResults = new ArrayList<>();
        for (Media result : movieResults.results) {
            MediaFullDetails media = TmdbUtils.getMediaFullDetailsFromTmdbMedia(tmdb, result);

            if(media != null) {
                mediaSearchResults.add(media);
            }

        }

        SearchResultFactory searchResultFactory = new SearchResultFactory();
        return searchResultFactory.create(searchParams.getPage(),  movieResults.total_pages,  movieResults.total_results, mediaSearchResults);
    }

    private MediaResultsPage searchMedia(SearchParams searchParams, Tmdb tmdb) throws IOException {
        Call<MediaResultsPage> call = tmdb.searchService().multi(searchParams.getQuery(), searchParams.getPage(), null, null, null);
        return call.execute().body();
    }
}
