import { Component, OnInit } from '@angular/core';
import {Movie} from "./shared/movie";
import {MovieService} from "./shared/movie.service";
import {Router} from "@angular/router";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {MovieDetailsModalComponent} from "./movie-details-modal/movie-details-modal.component";

@Component({
  selector: 'app-movie',
  templateUrl: 'movies.component.html',
  styleUrls: ['movies.component.css']
})
export class MoviesComponent implements OnInit {
  movies: Movie[];
  searchString: string;

  constructor(private router: Router, private movieService: MovieService, private modalService: NgbModal) { }

  ngOnInit() {
    //this.getMovies();
  }

  submitSearch(): void {
    this.movieService.getMoviesByFilter(this.searchString).then(movies => this.movies = movies);
  }

  getMovies(): void {
    this.movieService.getMovies().then(movies => this.movies = movies);
  }

  onSelectMovie(movie: Movie) {
    const modal = this.modalService.open(MovieDetailsModalComponent, {size: 'lg'});
    modal.componentInstance.movie = movie;
  }

  gotoMoviePage(movie: Movie) {
    this.router.navigate(['/movie', movie.imdbId])
  }

}
