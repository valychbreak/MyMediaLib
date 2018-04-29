import {Component, Input, OnInit} from '@angular/core';
import {SearchParams} from "../../../shared/search/search-params";
import {MovieService} from "../../../service/movie.service";
import {SearchComponentSection} from "../../search-component-section";
import {Movie} from "../../../shared/movie/movie";

@Component({
  selector: 'tvshow-search-section',
  templateUrl: './tvshow-search-section.component.html',
  styleUrls: ['./tvshow-search-section.component.css']
})
export class TvShowSearchSectionComponent extends SearchComponentSection<Movie> implements PageOriented, OnInit {
    private busy: any;

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
            this.busy = this.movieService.searchTvShow(this.searchParams.query, page).then(searchResult => {
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
