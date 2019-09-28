import {Component, OnInit} from '@angular/core';
import {AbstractForm} from "../../base/form";
import {SearchParams} from "../../shared/search/search-params";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'app-movie',
    templateUrl: 'movies.component.html',
    styleUrls: ['movies.component.scss']
})
export class MoviesComponent extends AbstractForm implements OnInit {
    searchParams: SearchParams;

    constructor(private router: Router, private activatedRoute: ActivatedRoute) {
        super()
    }

    ngOnInit() {
        let queryParam = this.activatedRoute.snapshot.queryParamMap.get('q');
        let pageParam = this.activatedRoute.snapshot.queryParamMap.get('p');

        this.searchParams = this.createSearchParams(queryParam, pageParam);
    }

    submitSearch(searchParams: SearchParams, isValid: boolean): void {
        this.searchParams = new SearchParams();
        this.searchParams.query = searchParams.query;
        this.searchParams.page = 1;

        this.changeUrlQueryParams(this.searchParams);
    }


    private changeUrlQueryParams(searchParams: SearchParams) {
        let queryParams = {
            q: searchParams.query,
            p: String(searchParams.page)
        };

        this.router.navigate([], {relativeTo: this.activatedRoute, queryParams, queryParamsHandling: "merge"})
    }

    private createSearchParams(searchQuery: string, pageParam: string): SearchParams {
        let searchParams = new SearchParams();

        if (searchQuery) {
            searchParams.query = searchQuery;
        }

        let page = parseInt(pageParam);
        searchParams.page = searchQuery && !isNaN(page) ? page : 1;

        return searchParams;
    }

}
