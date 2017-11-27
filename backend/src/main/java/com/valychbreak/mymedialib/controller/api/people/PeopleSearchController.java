package com.valychbreak.mymedialib.controller.api.people;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.dto.person.BasicPersonDTO;
import com.valychbreak.mymedialib.services.people.PeopleService;
import com.valychbreak.mymedialib.services.utils.SearchParamsBuilder;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class PeopleSearchController extends APIController {
    private PeopleService peopleService;

    @Autowired
    public PeopleSearchController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @RequestMapping(value = "/people/search", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<SearchResult<BasicPersonDTO>> searchPeople(
            @RequestParam(value = "q") String query,
            @RequestParam(value = "p", required = false) Integer page
    ) throws IOException {
        SearchParamsBuilder searchParamsBuilder = new SearchParamsBuilder();
        searchParamsBuilder.withQuery(query);
        searchParamsBuilder.withPage(page);

        SearchResult<BasicPersonDTO> searchResult = peopleService.search(searchParamsBuilder.build());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}
