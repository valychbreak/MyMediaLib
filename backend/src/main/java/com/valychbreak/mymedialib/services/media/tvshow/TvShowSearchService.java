package com.valychbreak.mymedialib.services.media.tvshow;

import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchResult;

import java.io.IOException;

public interface TvShowSearchService {
    SearchResult<MediaFullDetails> search(SearchParams searchParams) throws IOException;
}
