package com.valychbreak.mymedialib;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.FindResults;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.enumerations.ExternalSource;
import org.junit.Test;
import retrofit2.Call;

/**
 * Created by valych on 6/1/17.
 */
public class TestNewApi {
    private static final String API_KEY = "01e924145da414b33cdc651619bb694b";
    private static final Tmdb manager = new Tmdb(API_KEY);
    private static final String MOVIE2_IMDB_ID = "tt0361748";

    public Tmdb getManager() {
        return manager;
    }

    @Test
    public void test() throws Exception {
        Call<FindResults> call = getManager().findService().find(MOVIE2_IMDB_ID, ExternalSource.IMDB_ID, null);
        FindResults results = call.execute().body();
        System.out.println(results);
    }

    @Test
    public void testSearch() throws Exception {
        Call<MovieResultsPage> call = getManager().searchService().movie("Spider", null, null, null, null,
                null, null);
        MovieResultsPage movieResults = call.execute().body();
    }
}
