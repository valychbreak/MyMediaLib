package com.valychbreak.mymedialib.services.media.movie;

import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.exception.ExternalAPIException;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchResult;

public interface MovieSearchService {
    SearchResult<MediaFullDetails> search(SearchParams searchParams) throws ExternalAPIException;
}
