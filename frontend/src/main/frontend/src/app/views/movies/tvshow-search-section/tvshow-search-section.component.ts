import {Component, OnInit} from '@angular/core';
import {MovieService} from "../../../service/movie.service";
import {SearchComponentSection} from "../../search-component-section";
import {Movie} from "../../../shared/movie/movie";
import {SearchResult} from "../../../shared/search/search-result";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'tvshow-search-section',
  templateUrl: './tvshow-search-section.component.html',
  styleUrls: ['./tvshow-search-section.component.css']
})
export class TvShowSearchSectionComponent extends SearchComponentSection<Movie> implements PageOriented, OnInit {

    constructor(private movieService: MovieService, router: Router, activatedRoute: ActivatedRoute) {
        super(router, activatedRoute);
    }

    ngOnInit() {

    }

    doSearch(query: string, page: number): Promise<SearchResult<Movie>> {
        return this.movieService.searchTvShow(this.searchParams.query, page).then(searchResult => {
            return this.searchResult = searchResult;
        });
    }
}
