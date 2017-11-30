package com.valychbreak.mymedialib.services.utils;

import java.util.Collection;

public class SearchResult<T> {
    protected int page;
    protected int totalResults;
    protected int totalPages;
    protected Collection<T> items;

    public SearchResult(int page, int totalPages, int totalResults, Collection<T> items) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.items = items;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public Collection<T> getItems() {
        return items;
    }
}
