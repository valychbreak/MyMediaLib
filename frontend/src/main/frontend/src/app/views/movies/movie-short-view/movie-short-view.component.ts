import {Component, OnInit, Input} from '@angular/core';
import {Movie} from "../../../shared/movie/movie";
import {MovieDetailsModalComponent} from "../movie-details-modal/movie-details-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";
import {ImageUtils} from "../../utils/image-utils";
import {UserFavouritesService} from "../../../service/user-favourites.service";

@Component({
    selector: 'app-movie-short-view',
    templateUrl: './movie-short-view.component.html',
    styleUrls: ['./movie-short-view.component.scss']
})
export class MovieShortViewComponent implements OnInit {

    @Input()
    movie: Movie;

    isMediaFavorite: boolean;

    constructor(private router: Router, private modalService: NgbModal, private userFavouritesService: UserFavouritesService) {
    }

    ngOnInit() {
        this.isMediaFavorite = this.movie.isFavourite;
    }

    getImage(imagePath: string): string {
        return ImageUtils.getNoImageIfEmptyOrNull(imagePath);
    }

    onSelectMovie(movie: Movie) {
        const modal = this.modalService.open(MovieDetailsModalComponent, {size: 'lg'});
        modal.componentInstance.movie = movie;
    }

    gotoMoviePage(movie: Movie) {
        this.router.navigate(['/movie', movie.imdbId])
    }

    onFavoriteToggleChange(previousState: boolean) {
        if (previousState) {
            this.removeFromFavourites(this.movie);
        } else {
            this.addToFavourites(this.movie);
        }

        this.isMediaFavorite = !this.isMediaFavorite;
    }

    addToFavourites(movie: Movie) {
        movie.isFavourite = true;
        this.userFavouritesService.addMedia(movie).then(response => {
            console.log("Successfully added " + movie.title + " media to favorites")
        });
    }

    removeFromFavourites(movie: Movie) {
        movie.isFavourite = false;
        this.userFavouritesService.removeMedia(movie).then(response => {
            console.log("Successfully removed " + movie.title + " media from favorites")
        });
    }

    getMediaRating(media: Movie): string {
        if (media.stars > 0) {
            return media.stars + "/10";
        } else {
            return "Not rated"
        }
    }

    getTitleFontSize(length: number) {
        let maxSymbols = 17;
        if (length > maxSymbols) {
            //return 1 - (length - maxSymbols) * 0.03;
            return 0.65;
        } else {
            return 1;
        }
    }
}
