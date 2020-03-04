package com.valychbreak.mymedialib.services.media;

import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.exception.ExternalAPIException;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchResult;

public interface MediaSearchService {
    SearchResult<MediaFullDetails> search(SearchParams searchParams) throws ExternalAPIException;
}
