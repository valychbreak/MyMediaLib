import {Component, OnInit} from '@angular/core';
import {Movie} from "../../shared/movie/movie";
import {MovieService} from "../../service/movie.service";
import {Router} from "@angular/router";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {MovieDetailsModalComponent} from "./movie-details-modal/movie-details-modal.component";
import {AbstractForm} from "../../base/form";
import {Person} from "../../shared/person/person";
import {BasicMovie} from "../../shared/movie/basic-movie";
import {PeopleService} from "../../service/people.service";
import {SearchResult} from "../../shared/search/search-result";
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
