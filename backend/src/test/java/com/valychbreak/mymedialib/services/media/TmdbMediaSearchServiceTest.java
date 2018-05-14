package com.valychbreak.mymedialib.services.media;

import com.google.common.collect.Iterables;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.Media;
import com.uwetrottmann.tmdb2.entities.MediaResultsPage;
import com.uwetrottmann.tmdb2.services.SearchService;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchParamsBuilder;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import com.valychbreak.mymedialib.services.utils.TmdbService;
import com.valychbreak.mymedialib.testtools.tmdb.TmdbMediaFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static com.valychbreak.mymedialib.testtools.tmdb.BaseTvShowBuilder.aBaseTvShowBuilder;
import static com.valychbreak.mymedialib.testtools.tmdb.TmdbEntityMockHelper.mockTmdbSearchService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TmdbMediaSearchServiceTest {

    @Mock
    private Tmdb tmdb;

    @Mock
    private SearchService searchService;

    @Mock
    private TmdbService tmdbService;

    @Mock
    private UserMediaService userMediaService;

    private TmdbMediaSearchService mediaSearchService;

    @Before
    public void setup() {
        mediaSearchService = new TmdbMediaSearchService(tmdb, tmdbService, userMediaService);
    }

    @Test
    public void searchReturnsMediaFullDetails() throws Exception {

        BaseTvShow baseTvShow = aBaseTvShowBuilder().setId(1).setName("Test tvshow").createBaseTvShow();
        Media media = new TmdbMediaFactory().createMedia(baseTvShow);

        MediaResultsPage mediaResultsPage = new MediaResultsPage();
        mediaResultsPage.results = Arrays.asList(media);
        mediaResultsPage.total_pages = 1;
        mediaResultsPage.total_results = 1;

        MediaFullDetailsImpl mockedMediaDetails = createMediaFullDetails("Test tvshow", "tt12345678", false);
        when(userMediaService.isUserFavorite(any(User.class), eq(mockedMediaDetails))).thenReturn(true);
        when(tmdbService.getMediaDetails(any(Media.class))).thenReturn(Optional.of(mockedMediaDetails));
        when(tmdb.searchService()).thenReturn(searchService);
        mockTmdbSearchService(searchService, mediaResultsPage);

        SearchParams searchParams = new SearchParamsBuilder().withQuery("Test tvshow").build();
        SearchResult<MediaFullDetails> searchResult = mediaSearchService.search(searchParams);
        Collection<MediaFullDetails> mediaFullDetails = searchResult.getItems();
        assertThat(Iterables.getFirst(mediaFullDetails, null)).isNotNull();
    }

    private MediaFullDetailsImpl createMediaFullDetails(String title, String imdbId, boolean isFavourite) {
        MediaFullDetailsImpl mockedMediaDetails = new MediaFullDetailsImpl();
        mockedMediaDetails.setTitle(title);
        mockedMediaDetails.setImdbId(imdbId);
        mockedMediaDetails.setFavourite(isFavourite);
        return mockedMediaDetails;
    }
}