import {Component, OnInit, Input} from '@angular/core';
import {Movie} from "../../../shared/movie/movie";
import {MovieDetailsModalComponent} from "../movie-details-modal/movie-details-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";
import {ImageUtils} from "../../utils/image-utils";

@Component({
    selector: 'app-movie-short-view',
    templateUrl: './movie-short-view.component.html',
    styleUrls: ['./movie-short-view.component.css']
})
export class MovieShortViewComponent implements OnInit {

    @Input()
    movie: Movie;

    constructor(private router: Router, private modalService: NgbModal) {
    }

    ngOnInit() {
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

}
