import {Component, Input, OnInit} from '@angular/core';
import {SearchParams} from "../../../shared/search/search-params";
import {Movie} from "../../../shared/movie/movie";
import {MovieService} from "../../../service/movie.service";
import {SearchComponentSection} from "../../search-component-section";

@Component({
  selector: 'media-search-section',
  templateUrl: './media-search-section.component.html',
  styleUrls: ['./media-search-section.component.css']
})
export class MediaSearchSectionComponent extends SearchComponentSection<Movie> implements PageOriented, OnInit {
    private busy: any;

    constructor(private movieService: MovieService) {
        super();
    }

    ngOnInit() {
        this.mockData();
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
            this.busy = this.movieService.searchMedia(this.searchParams.query, page).then(searchResult => {
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
