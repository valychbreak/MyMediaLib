package com.valychbreak.mymedialib.services.utils;

public class SearchParamsBuilder {
    private SearchParams searchParams;

    public SearchParamsBuilder() {
        searchParams = new SearchParams();
    }

    public SearchParamsBuilder withPage(Integer page) {
        if (page == null) {
            searchParams.page = 1;
        } else {
            searchParams.page = page;
        }
        return this;
    }

    public SearchParamsBuilder withItemsPerPage(int itemsPerPage) {
        searchParams.itemsPerPage = itemsPerPage;
        return this;
    }

    public SearchParamsBuilder withQuery(String query) {
        searchParams.query = query;
        return this;
    }

    public SearchParams build() {
        return searchParams;
    }
}
