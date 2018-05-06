
import {SearchParams} from "../shared/search/search-params";
import {SearchResult} from "../shared/search/search-result";

export class SearchComponentSection<T> {
    private _searchParams: SearchParams;

    protected searchResult: SearchResult<T>;


    constructor() {
        if (!this.searchResult) {
            this.searchResult = new SearchResult();
            this.searchResult.page = 1;
            this.searchResult.totalPages = 1;
        }
    }

    public getSearchParams(): SearchParams {
        return this._searchParams;
    }

    public setSearchParams(value: SearchParams) {
        this._searchParams = value;
    }
}