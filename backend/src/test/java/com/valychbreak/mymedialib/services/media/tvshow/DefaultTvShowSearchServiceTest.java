package com.valychbreak.mymedialib.services.media.tvshow;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.TvShowResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;
import com.uwetrottmann.tmdb2.services.SearchService;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.services.media.movie.DefaultMovieSearchService;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchParamsBuilder;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTvShowSearchServiceTest {

    private static final int TOTAL_PAGES = 5;
    private static final int TOTAL_RESULTS = 100;
    private static final int CURRENT_PAGE = 2;

    @Mock
    private Tmdb tmdb;

    @Mock
    private SearchService searchService;

    private DefaultTvShowSearchService tvShowSearchService;

    @Before
    public void setUp() throws Exception {
        when(tmdb.searchService()).thenReturn(searchService);
        setupTmdbSearchService();

        tvShowSearchService = new DefaultTvShowSearchService(tmdb);
    }

    @Test
    public void search() throws Exception {
        SearchParams searchParams = new SearchParamsBuilder().withPage(CURRENT_PAGE).withQuery("Batman returns").build();
        SearchResult<MediaFullDetails> searchResult = tvShowSearchService.search(searchParams);

        verify(searchService, times(1)).tv(eq("Batman returns"), eq(CURRENT_PAGE), any(String.class), any(Integer.class), any(String.class));
        assertThat(searchResult.getTotalPages()).isEqualTo(TOTAL_PAGES);
        assertThat(searchResult.getTotalResults()).isEqualTo(TOTAL_RESULTS);
        assertThat(searchResult.getPage()).isEqualTo(CURRENT_PAGE);
    }

    private void setupTmdbSearchService() throws java.io.IOException {
        TvShowResultsPage tvShowResultsPage = new TvShowResultsPage();
        tvShowResultsPage.results = new ArrayList<>();
        tvShowResultsPage.total_pages = TOTAL_PAGES;
        tvShowResultsPage.total_results = TOTAL_RESULTS;
        Call<TvShowResultsPage> mockedCall = mock(Call.class);
        when(mockedCall.execute()).thenReturn(Response.success(tvShowResultsPage));
        when(searchService.tv(any(String.class), any(Integer.class), any(String.class), any(Integer.class), any(String.class)))
                .thenReturn(mockedCall);
    }
}