import {Component, OnInit} from '@angular/core';
import {Movie} from "../../../shared/movie/movie";
import {MovieService} from "../../../service/movie.service";
import {SearchComponentSection} from "../../search-component-section";
import {SearchResult} from "../../../shared/search/search-result";

@Component({
  selector: 'media-search-section',
  templateUrl: './media-search-section.component.html',
  styleUrls: ['./media-search-section.component.css']
})
export class MediaSearchSectionComponent extends SearchComponentSection<Movie> implements OnInit {

    constructor(private movieService: MovieService) {
        super();
    }

    ngOnInit() {
        //this.mockData();
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

    doSearch(query: string, page: number): Promise<SearchResult<Movie>> {
        return this.movieService.searchMedia(this.searchParams.query, page).then(searchResult => {
            return this.searchResult = searchResult;
        });
    }


}
