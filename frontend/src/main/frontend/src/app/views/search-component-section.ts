import {SearchParams} from "../shared/search/search-params";
import {SearchResult} from "../shared/search/search-result";
import {Input} from "@angular/core";

export abstract class SearchComponentSection<T> implements PageOriented {
    private _searchParams: SearchParams;
    private _previousSearchQuery: string;

    protected searchResult: SearchResult<T>;
    protected busyLoading: any;


    protected constructor() {
        if (!this.searchResult) {
            this.searchResult = new SearchResult();
            this.searchResult.page = 1;
            this.searchResult.totalPages = 1;
        }
    }

    onPageChange(): void {
        this.applySearch();
    }

    protected applySearch() {
        if (this.searchParams && this.searchParams.query) {
            console.log("[Search] Start searching...");

            let page = this.getPage(this.searchParams, this.searchResult);
            this.busyLoading = this.doSearch(this.searchParams.query, page);

            this._previousSearchQuery = this.searchParams.query;
        }
    }

    private getPage(searchParams: SearchParams, searchResult: SearchResult<T>) {
        /*if (searchResult && searchParams.query === this._previousSearchQuery) {
            return searchResult.page;
        } else {
            return searchParams.page;
        }*/

        return searchParams.page;
    }

    get page(): number {
        return this.searchParams.page;
    }

    set page(pageNumber: number) {
        this.searchParams.page = pageNumber;
    }

    @Input()
    set searchParams(searchParams: SearchParams) {
         this._searchParams = searchParams;
        this.applySearch();
    }

    get searchParams(): SearchParams {
        return this._searchParams;
    }

    abstract doSearch(query: string, page: number): Promise<SearchResult<T>>;
}