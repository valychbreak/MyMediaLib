package com.valychbreak.mymedialib.services.media.movie;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.*;
import com.uwetrottmann.tmdb2.services.MoviesService;
import com.uwetrottmann.tmdb2.services.SearchService;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchParamsBuilder;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import com.valychbreak.mymedialib.testtools.TmdbMovieBuilder;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
// TODO: rewrite test as DefaultTvShowSearchServiceTest
public class DefaultMovieSearchServiceTest {

    @Mock
    private Tmdb tmdb;

    @Mock
    private SearchService searchService;

    private DefaultMovieSearchService movieSearchService;

    @Mock
    private MoviesService movieService;

    @Before
    public void setUp() throws Exception {
        when(tmdb.searchService()).thenReturn(searchService);
        when(tmdb.moviesService()).thenReturn(movieService);

        setupTmdbMovieService();
        setupTmdbSearchService();

        movieSearchService = new DefaultMovieSearchService(tmdb);
    }

    @Test
    public void search() throws Exception {
        SearchParams searchParams = new SearchParamsBuilder().withPage(1).withQuery("query").build();
        SearchResult<MediaFullDetails> searchResult = movieSearchService.search(searchParams);
        assertThat(searchResult).isNotNull();
        assertThat(searchResult.getTotalPages()).isEqualTo(1);
        assertThat(searchResult.getTotalResults()).isEqualTo(1);

        MediaFullDetailsImpl mediaFullDetails = getExpectedMediaFullDetails();
        assertThat(searchResult.getItems()).hasSize(1);
        assertThat(searchResult.getItems().iterator().next()).isEqualToComparingFieldByField(mediaFullDetails);
    }

    private MediaFullDetailsImpl getExpectedMediaFullDetails() {
        MediaFullDetailsImpl mediaFullDetails = new MediaFullDetailsImpl();
        mediaFullDetails.setTitle("Fight club");
        mediaFullDetails.setGenre("Adventure");
        mediaFullDetails.setDuration("116");
        mediaFullDetails.setImagePath("");
        mediaFullDetails.setStars("9.66");
        mediaFullDetails.setReviews("1337");
        mediaFullDetails.setType("Movie");
        mediaFullDetails.setImdbId("tt123");
        return mediaFullDetails;
    }

    private void setupTmdbSearchService() throws java.io.IOException {
        BaseMovie baseMovie = new BaseMovie();
        baseMovie.id = 1;
        baseMovie.title = "Fight club";
        baseMovie.media_type = "Movie";

        ArrayList<BaseMovie> baseMovies = new ArrayList<>();
        baseMovies.add(baseMovie);

        MovieResultsPage movieResultsPage = new MovieResultsPage();
        movieResultsPage.total_pages = 1;
        movieResultsPage.total_results = 1;
        movieResultsPage.results = baseMovies;
        Response<MovieResultsPage> response = Response.success(movieResultsPage);

        Call<MovieResultsPage> mockedCall = mock(Call.class);
        when(mockedCall.execute()).thenReturn(response);
        when(searchService.movie(any(String.class), any(Integer.class), any(String.class), any(Boolean.class),
                any(Integer.class), any(Integer.class), any(String.class)))
                .thenReturn(mockedCall);
    }

    private void setupTmdbMovieService() throws java.io.IOException {
        Movie fullMovie = new TmdbMovieBuilder()
                .setId(1)
                .setTitle("Fight club")
                .setMediaType("Movie")
                .setBackdropPath("")
                .setImdbId("tt123")
                .setVoteAverage(9.66D)
                .setVoteCount(1337)
                .build();

        Response<Movie> movieResponse = Response.success(fullMovie);

        Call<Movie> mockedMovieCall = mock(Call.class);
        when(mockedMovieCall.execute()).thenReturn(movieResponse);
        when(movieService.summary(any(Integer.class), any(String.class), any(AppendToResponse.class))).thenReturn(mockedMovieCall);
    }

}