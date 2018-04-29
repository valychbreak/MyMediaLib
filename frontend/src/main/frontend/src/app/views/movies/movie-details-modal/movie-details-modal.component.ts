import {Component, OnInit, Input, Injector} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Movie} from "../../../shared/movie/movie";
import {Router} from "@angular/router";
import {UserFavouritesService} from "../../../service/user-favourites.service";
import {AbstractMovieDetails} from "../../../shared/movie/base-movie-details";

@Component({
    selector: 'app-movie-details-modal',
    templateUrl: './movie-details-modal.component.html',
    styleUrls: ['./movie-details-modal.component.css']
})
export class MovieDetailsModalComponent extends AbstractMovieDetails implements OnInit {

    @Input()
    movie: Movie;

    constructor(public activeModal: NgbActiveModal, private router: Router, private injector: Injector) {
        super(injector);
    }

    ngOnInit() {
    }

    addToFavourites(movie: Movie) {
        movie.isFavourite = true;
        this.userFavouritesService.addMedia(movie);
    }

    removeFromFavourites(movie: Movie) {
        movie.isFavourite = false;
        this.userFavouritesService.removeMedia(movie);
    }

    gotoMoviePage() {
        this.activeModal.dismiss();
        this.router.navigate(['/movie', this.movie.imdbId])
    }

}
