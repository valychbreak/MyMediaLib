package com.valychbreak.mymedialib.services.utils;

import java.util.ArrayList;
import java.util.Collection;

// todo: convert to builder?
public class SearchResultFactory {
    public <T> SearchResult<T> create(int page, int totalPages, int totalResults, Collection<T> items) {
        return new SearchResult<>(page, totalPages, totalResults, new ArrayList<>(items));
    }
}
