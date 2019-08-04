package com.valychbreak.mymedialib.services.media.movie;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;
import com.uwetrottmann.tmdb2.services.SearchService;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchParamsBuilder;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import com.valychbreak.mymedialib.services.utils.TmdbService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TmdbMovieSearchServiceTest {

    private static final int CURRENT_PAGE = 3;
    private static final int TOTAL_PAGES = 1;
    private static final int TOTAL_RESULTS = 1;


    @Mock
    private Tmdb tmdb;

    @Mock
    private SearchService searchService;

    @Mock
    private TmdbService tmdbService;

    private TmdbMovieSearchService movieSearchService;

    @Before
    public void setUp() {
        when(tmdb.searchService()).thenReturn(searchService);

        movieSearchService = new TmdbMovieSearchService(tmdb, tmdbService);
    }

    @Test
    public void search() throws Exception {
        setupTmdbSearchService(TOTAL_PAGES, TOTAL_RESULTS);

        SearchParams searchParams = new SearchParamsBuilder()
                .withPage(CURRENT_PAGE)
                .withQuery("Spider-man")
                .build();
        SearchResult<MediaFullDetails> searchResult = movieSearchService.search(searchParams);

        verify(searchService).movie(eq("Spider-man"), eq(CURRENT_PAGE), eq(null), eq(null), eq(null), eq(null), eq(null));
        assertThat(searchResult).isNotNull();
        assertThat(searchResult.getTotalPages()).isEqualTo(TOTAL_PAGES);
        assertThat(searchResult.getTotalResults()).isEqualTo(TOTAL_RESULTS);
    }

    private void setupTmdbSearchService(int totalPages, int totalResults) throws java.io.IOException {
        MovieResultsPage movieResultsPage = new MovieResultsPage();
        movieResultsPage.total_pages = totalPages;
        movieResultsPage.total_results = totalResults;
        movieResultsPage.results = new ArrayList<>();
        Response<MovieResultsPage> response = Response.success(movieResultsPage);

        Call<MovieResultsPage> mockedCall = mock(Call.class);
        when(mockedCall.execute()).thenReturn(response);
        when(searchService.movie(any(String.class), any(Integer.class), eq(null), eq(null), eq(null), eq(null), eq(null)))
                .thenReturn(mockedCall);
    }

}