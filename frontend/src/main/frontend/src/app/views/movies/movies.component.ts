import {Component, OnInit} from '@angular/core';
import {AbstractForm} from "../../base/form";
import {SearchParams} from "../../shared/search/search-params";

@Component({
    selector: 'app-movie',
    templateUrl: 'movies.component.html',
    styleUrls: ['movies.component.css']
})
export class MoviesComponent extends AbstractForm implements OnInit {
    searchParams: SearchParams;

    constructor() {
        super()
    }

    ngOnInit() {
        this.searchParams = new SearchParams();
        this.searchParams.page = 1;

    }

    submitSearch(searchParams: SearchParams, isValid: boolean): void {
        this.searchParams = new SearchParams();
        this.searchParams.query = searchParams.query;
        this.searchParams.page = searchParams.page;
    }

}
