package com.valychbreak.mymedialib.services.people;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BasePerson;
import com.uwetrottmann.tmdb2.entities.Media;
import com.uwetrottmann.tmdb2.entities.PersonResultsPage;
import com.valychbreak.mymedialib.dto.movie.BasicMovieDTO;
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
        PersonResultsPage tmdbSearchResults = getTmdbSearchResults(searchParams);

        List<BasicPersonDTO> results = new ArrayList<>(tmdbSearchResults.results.size());
        for (BasePerson basePerson : tmdbSearchResults.results) {
            BasicPersonDTO basicPersonDTO = new BasicPersonDTO(new Long(basePerson.id), basePerson.name, new ArrayList<>());
            addKnowFor(basicPersonDTO, basePerson.known_for);

            results.add(basicPersonDTO);
        }

        return new SearchResult<>(tmdbSearchResults.total_results, tmdbSearchResults.total_pages, results);
    }

    private void addKnowFor(BasicPersonDTO basicPersonDTO, List<Media> knownFor) {
        for (Media media : knownFor) {
            BasicMovieDTO basicMovieDTO = new BasicMovieDTO();
            switch (media.media_type) {
                case MOVIE:
                    basicMovieDTO.setName(media.movie.title);
                    break;
                case TV:
                    basicMovieDTO.setName(media.tvShow.name);
            }

            basicPersonDTO.getKnownFor().add(basicMovieDTO);
        }
    }

    private PersonResultsPage getTmdbSearchResults(SearchParams searchParams) throws IOException {
        Call<PersonResultsPage> peopleSearchResult = tmdb.searchService().person(searchParams.getQuery(), searchParams.getPage(), null, null);
        return peopleSearchResult.execute().body();
    }
}
