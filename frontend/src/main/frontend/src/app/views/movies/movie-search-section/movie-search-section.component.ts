import {Component, OnInit} from '@angular/core';
import {Movie} from "../../../shared/movie/movie";
import {MovieService} from "../../../service/movie.service";
import {SearchComponentSection} from "../../search-component-section";
import {SearchResult} from "../../../shared/search/search-result";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'movie-search-section',
    templateUrl: './movie-search-section.component.html',
    styleUrls: ['./movie-search-section.component.css']
})
export class MovieSearchSectionComponent extends SearchComponentSection<Movie> implements PageOriented, OnInit {

    constructor(private movieService: MovieService, router: Router, activatedRoute: ActivatedRoute) {
        super(router, activatedRoute);
    }

    ngOnInit() {

    }

    doSearch(query: string, page: number): Promise<SearchResult<Movie>> {
        return this.movieService.searchMovie(this.searchParams.query, page).then(searchResult => {
            return this.searchResult = searchResult;
        });
    }
}
