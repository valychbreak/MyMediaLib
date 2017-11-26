package com.valychbreak.mymedialib.services.people;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BasePerson;
import com.uwetrottmann.tmdb2.entities.PersonResultsPage;
import com.valychbreak.mymedialib.dto.person.BasicPersonDTO;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchResult;
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
        List<BasicPersonDTO> results = new ArrayList<>();

        Call<PersonResultsPage> peopleSearchResult = tmdb.searchService().person(searchParams.getQuery(), searchParams.getPage(), null, null);
        PersonResultsPage body = peopleSearchResult.execute().body();
        for (BasePerson basePerson : body.results) {
            BasicPersonDTO basicPersonDTO = new BasicPersonDTO(new Long(basePerson.id), basePerson.name, new ArrayList<>());
            results.add(basicPersonDTO);
        }

        return new SearchResult<>(body.total_results, body.total_pages, results);
    }
}
