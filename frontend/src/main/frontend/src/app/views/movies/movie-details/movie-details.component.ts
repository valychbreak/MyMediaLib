import {Component, OnInit, Injector} from '@angular/core';
import {Movie} from "../../../shared/movie/movie";
import {MovieService} from "../../../service/movie.service";
import {ActivatedRoute} from "@angular/router";
import {AbstractMovieDetails} from "../../../shared/movie/base-movie-details";

@Component({
    selector: 'app-movie-view',
    templateUrl: 'movie-details.component.html',
    styleUrls: ['movie-details.component.css']
})
export class MovieViewComponent extends AbstractMovieDetails implements OnInit {
    movie = new Movie();

    constructor(private movieService: MovieService, private route: ActivatedRoute, private injector: Injector) {
        super(injector);
    }

    ngOnInit() {
        /*let id = +this.route.snapshot.params['id'];
        if(!isNaN(id)) {
          console.log(id);
          this.movieService.getMovie(id)
            .then((movie: Movie) => this.movie = movie);
        }*/
        let id = this.route.snapshot.params['id'];
        this.movieService.getMovieByImdbId(id)
            .then((movie: Movie) => this.movie = movie);
    }

}
