import { Component, OnInit } from '@angular/core';
import {Movie} from "./shared/movie";
import {MovieService} from "./shared/movie.service";
import {Router} from "@angular/router";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {MovieDetailsModalComponent} from "./movie-details-modal/movie-details-modal.component";
import {AbstractForm} from "../base/form";
import {MovieSearch} from "./shared/movie-search";

@Component({
  selector: 'app-movie',
  templateUrl: 'movies.component.html',
  styleUrls: ['movies.component.css']
})
export class MoviesComponent extends AbstractForm implements OnInit {
  movies: Movie[];
  searchString: string;
  movieSearch: MovieSearch;

  constructor(private router: Router, private movieService: MovieService, private modalService: NgbModal) {
    super()
  }

  ngOnInit() {
    this.movieSearch = new MovieSearch();
    //this.getMovies();
  }

  submitSearch(movieSearch: MovieSearch, isValid: boolean): void {
    this.movieService.getMoviesByFilter(movieSearch.query).then(movies => this.movies = movies);
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
