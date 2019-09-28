import {SearchParams} from "../shared/search/search-params";
import {SearchResult} from "../shared/search/search-result";
import {Input} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";

export abstract class SearchComponentSection<T> implements PageOriented {
    private _searchParams: SearchParams;

    protected searchResult: SearchResult<T>;
    protected busyLoading: any;


    protected constructor(private router: Router, private activatedRoute: ActivatedRoute) {
        if (!this.searchResult) {
            this.searchResult = new SearchResult();
            this.searchResult.page = 1;
            this.searchResult.totalPages = Infinity;
        }
    }

    onPageChange(): void {
        this.router.navigate([], {relativeTo: this.activatedRoute, queryParams: {p: this.page}, queryParamsHandling: "merge"});
        this.applySearch();
    }

    protected applySearch() {
        if (this._searchParams && this._searchParams.query) {
            console.log("[Search] Start searching...");

            this.busyLoading = this.doSearch(this._searchParams.query, this.page);
        }
    }

    get page(): number {
        return this._searchParams.page;
    }

    set page(pageNumber: number) {
        this._searchParams.page = pageNumber;
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