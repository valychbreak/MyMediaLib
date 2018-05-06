package com.valychbreak.mymedialib.services.people;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.BasePerson;
import com.uwetrottmann.tmdb2.entities.Media;
import com.uwetrottmann.tmdb2.entities.PersonResultsPage;
import com.uwetrottmann.tmdb2.enumerations.MediaType;
import com.uwetrottmann.tmdb2.services.SearchService;
import com.valychbreak.mymedialib.dto.movie.BasicMediaDTO;
import com.valychbreak.mymedialib.dto.person.BasicPersonDTO;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPeopleServiceTest {

    private PeopleService peopleService;

    @Mock
    private Tmdb tmdb;

    @Mock
    private SearchService searchService;

    @Before
    public void setUp() throws Exception {
        when(tmdb.searchService()).thenReturn(searchService);
        Call<PersonResultsPage> mockedCall = mock(Call.class);
        when(searchService.person(eq("Brad"), any(Integer.class), any(Boolean.class), any(String.class))).thenReturn(mockedCall);

        PersonResultsPage personResultsPage = new PersonResultsPage();
        personResultsPage.total_results = 100;
        personResultsPage.total_pages = 5;
        personResultsPage.page = 5;

        List<Media> known_for = Arrays.asList(createMediaWithFightClubMovie());
        BasePerson basePerson = createBasePerson("Brad Pitt", 123, known_for);

        personResultsPage.results = Arrays.asList(basePerson);
        Response<PersonResultsPage> response = Response.success(personResultsPage);
        when(mockedCall.execute()).thenReturn(response);

        peopleService = new DefaultPeopleService(tmdb);
    }

    private Media createMediaWithFightClubMovie() {
        Media media = new Media();
        media.media_type = MediaType.MOVIE;
        BaseMovie movie = new BaseMovie();
        movie.title = "Fight Club";
        media.movie = movie;
        movie.media_type = "Movie";
        return media;
    }

    private BasePerson createBasePerson(String name, int id, List<Media> media) {
        BasePerson basePerson = new BasePerson();
        basePerson.adult = true;
        basePerson.id = id;
        basePerson.name = name;
        basePerson.known_for = media;
        return basePerson;
    }

    @Test
    public void search() throws Exception {
        SearchParams searchParams = new SearchParamsBuilder().withQuery("Brad").withItemsPerPage(20).withPage(3).build();
        SearchResult<BasicPersonDTO> searchResult = peopleService.search(searchParams);

        assertThat(searchResult.getTotalPages()).isEqualTo(5);
        assertThat(searchResult.getTotalResults()).isEqualTo(100);
        assertThat(searchResult.getItems().size()).isEqualTo(1);

        BasicPersonDTO basicPersonDTO = searchResult.getItems().iterator().next();
        BasicMediaDTO basicMediaDTO = basicPersonDTO.getKnownFor().get(0);
        assertThat(basicMediaDTO.getTitle()).isEqualTo("Fight Club");
        assertThat(basicMediaDTO.getMediaType()).isEqualTo("Movie");
    }

}