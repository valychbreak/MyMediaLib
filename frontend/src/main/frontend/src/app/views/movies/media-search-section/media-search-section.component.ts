import {Component, OnInit} from '@angular/core';
import {Movie} from "../../../shared/movie/movie";
import {MovieService} from "../../../service/movie.service";
import {SearchComponentSection} from "../../search-component-section";
import {SearchResult} from "../../../shared/search/search-result";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'media-search-section',
  templateUrl: './media-search-section.component.html',
  styleUrls: ['./media-search-section.component.css']
})
export class MediaSearchSectionComponent extends SearchComponentSection<Movie> implements OnInit {

    constructor(private movieService: MovieService, router: Router, activatedRoute: ActivatedRoute) {
        super(router, activatedRoute);
    }

    ngOnInit() {

    }

    doSearch(query: string, page: number): Promise<SearchResult<Movie>> {
        return this.movieService.searchMedia(this.searchParams.query, page).then(searchResult => {
            return this.searchResult = searchResult;
        });
    }


}
