package com.valychbreak.mymedialib.services.utils;

import java.util.List;

public class SearchResult<T> {
    protected int totalResults;
    protected int totalPages;
    protected List<T> items;

    public SearchResult(int totalResults, int totalPages, List<T> items) {
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

    public List<T> getItems() {
        return items;
    }
}
