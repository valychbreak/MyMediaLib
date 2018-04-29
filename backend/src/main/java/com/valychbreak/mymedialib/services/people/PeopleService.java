package com.valychbreak.mymedialib.services.people;

import com.valychbreak.mymedialib.dto.person.BasicPersonDTO;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchResult;

import java.io.IOException;

public interface PeopleService {
    SearchResult<BasicPersonDTO> search(SearchParams searchParams) throws IOException;
}
