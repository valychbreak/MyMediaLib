package com.valychbreak.mymedialib.services.people;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BasePerson;
import com.uwetrottmann.tmdb2.entities.Media;
import com.uwetrottmann.tmdb2.entities.PersonResultsPage;
import com.valychbreak.mymedialib.dto.movie.BasicMediaDTO;
import com.valychbreak.mymedialib.dto.person.BasicPersonDTO;
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
public class DefaultPeopleService implements PeopleService {

    private Tmdb tmdb;

    @Autowired
    public DefaultPeopleService(Tmdb tmdb) {
        this.tmdb = tmdb;
    }

    @Override
    public SearchResult<BasicPersonDTO> search(SearchParams searchParams) throws IOException {
        PersonResultsPage tmdbSearchResults = getTmdbSearchResults(searchParams);

        List<BasicPersonDTO> results = new ArrayList<>(tmdbSearchResults.results.size());
        for (BasePerson basePerson : tmdbSearchResults.results) {
            String posterImage = TmdbUtils.TMDB_IMAGE_BASE_URL + basePerson.profile_path;
            BasicPersonDTO basicPersonDTO = new BasicPersonDTO(new Long(basePerson.id), basePerson.name, posterImage, basePerson.popularity, new ArrayList<>());
            addKnownFor(basicPersonDTO, basePerson.known_for);

            results.add(basicPersonDTO);
        }

        SearchResultFactory searchResultFactory = new SearchResultFactory();
        return searchResultFactory.create(searchParams.getPage(), tmdbSearchResults.total_pages, tmdbSearchResults.total_results, results);
    }

    private void addKnownFor(BasicPersonDTO basicPersonDTO, List<Media> knownFor) {
        for (Media media : knownFor) {
            BasicMediaDTO basicMediaDTO = new BasicMediaDTO(media);
            basicPersonDTO.getKnownFor().add(basicMediaDTO);
        }
    }

    private PersonResultsPage getTmdbSearchResults(SearchParams searchParams) throws IOException {
        Call<PersonResultsPage> peopleSearchResult = tmdb.searchService().person(
                searchParams.getQuery(), searchParams.getPage(), null, null
        );
        return peopleSearchResult.execute().body();
    }
}
