package com.valychbreak.mymedialib.services.utils;

public class SearchParams {
    protected String query;
    protected int page;
    protected int itemsPerPage;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    @Override
    public String toString() {
        return "SearchParams{" +
                "query='" + query + '\'' +
                ", page=" + page +
                ", itemsPerPage=" + itemsPerPage +
                '}';
    }
}
