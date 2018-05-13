package com.valychbreak.mymedialib.testtools.tmdb;

import com.uwetrottmann.tmdb2.entities.MediaResultsPage;
import com.uwetrottmann.tmdb2.services.SearchService;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TmdbEntityMockHelper {
    public static SearchService mockTmdbSearchService(MediaResultsPage mediaResultsPage) throws IOException {
        SearchService searchService = mock(SearchService.class);

        Call<MediaResultsPage> call = mock(Call.class);
        when(searchService.multi(any(String.class), any(Integer.class), any(String.class), any(Boolean.class), any(String.class))).thenReturn(call);

        Response<MediaResultsPage> response = Response.success(mediaResultsPage);
        when(call.execute()).thenReturn(response);

        return searchService;
    }
}
