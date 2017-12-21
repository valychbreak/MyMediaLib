import {Component, Input, OnInit} from '@angular/core';
import {SearchParams} from "../../../shared/search/search-params";
import {Movie} from "../../../shared/movie/movie";
import {MovieService} from "../../../service/movie.service";
import {SearchComponentSection} from "../../search-component-section";

@Component({
    selector: 'movie-search-section',
    templateUrl: './movie-search-section.component.html',
    styleUrls: ['./movie-search-section.component.css']
})
export class MovieSearchSectionComponent extends SearchComponentSection<Movie> implements PageOriented, OnInit {

    constructor(private movieService: MovieService) {
        super();
    }

    ngOnInit() {

        this.mockData();

        // this.applySearch();

        console.log("ngOnInit called");
    }

    private mockData() {
        let movie = new Movie();
        movie.title = "Fight club";
        movie.genre = "Action";
        movie.imagePath = "https://image.tmdb.org/t/p/w320/ejYIW1enUcGJ9GS3Bs34mtONwWS.jpg";

        let movie2 = new Movie();
        movie2.title = "Fight club 1234";
        movie2.genre = "Action";
        movie2.imagePath = "";
        this.searchResult.items = [movie, movie2];
    }


    onPageChange(): void {
        this.applySearch();
    }

    applySearch() {
        if (this.searchParams && this.searchParams.query) {
            console.log("[Movie] Search is activated");

            let page = this.searchResult ? this.searchResult.page : this.searchParams.page;
            this.movieService.searchMedia(this.searchParams.query, page).then(searchResult => {
                this.searchResult = searchResult;
            });
        }
    }

    @Input()
    set searchParams(searchParams: SearchParams) {
        this.setSearchParams(searchParams);

        this.applySearch();
    }

    get searchParams(): SearchParams {
        return this.getSearchParams()
    }
}
