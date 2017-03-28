import {Component, OnInit, Input} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Movie} from "../shared/movie";
import {Router} from "@angular/router";
import {UserFavouritesService} from "../../users/shared/user-favourites.service";

@Component({
  selector: 'app-movie-details-modal',
  templateUrl: './movie-details-modal.component.html',
  styleUrls: ['./movie-details-modal.component.css']
})
export class MovieDetailsModalComponent implements OnInit {

  @Input()
  movie: Movie;

  constructor(public activeModal: NgbActiveModal, private router: Router, private userFavouritesService: UserFavouritesService) {}

  ngOnInit() {
  }

  addToFavourites() {
    this.userFavouritesService.addMedia(this.movie);
  }

  gotoMoviePage() {
    this.activeModal.dismiss();
    this.router.navigate(['/movie', this.movie.id])
  }

}
