import { Component, OnInit } from '@angular/core';
import {Movie} from "../../shared/movie/movie";
import {MovieService} from "../../shared/movie/movie.service";
import {Router} from "@angular/router";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {MovieDetailsModalComponent} from "./movie-details-modal/movie-details-modal.component";
import {AbstractForm} from "../../base/form";
import {MovieSearch} from "../../shared/movie/movie-search";
import {Person} from "../../shared/person/person";
import {BasicMovie} from "../../shared/movie/basic-movie";

@Component({
  selector: 'app-movie',
  templateUrl: 'movies.component.html',
  styleUrls: ['movies.component.css']
})
export class MoviesComponent extends AbstractForm implements OnInit {
  movies: Movie[];
  persons: Person[];
  searchString: string;
  movieSearch: MovieSearch;

  constructor(private router: Router, private movieService: MovieService, private modalService: NgbModal) {
    super()
  }

  ngOnInit() {
    this.movieSearch = new MovieSearch();

    this.mockData();
      //this.getMovies();
  }

    private mockData() {
      let movie = new Movie();
      movie.title = "Fight club";
      movie.genre = "Action";
      movie.imagePath = "https://image.tmdb.org/t/p/w320/ejYIW1enUcGJ9GS3Bs34mtONwWS.jpg";
      this.movies = [movie];

        this.persons = [
            this.createPerson(),
            this.createPerson(),
            this.createPerson(),
            this.createPerson()
        ];
    }

    private createPerson() {
        let person = new Person();
        person.name = "Brad Pitt";
        person.isAdult = true;
        person.image = "https://image.tmdb.org/t/p/w320/ejYIW1enUcGJ9GS3Bs34mtONwWS.jpg";

        person.knownFor = [
            this.createBasicMovie("Fight club", "1996"),
            this.createBasicMovie("Spider man", "2004"),
            this.createBasicMovie("The amazing spider-man", "2013")
        ];
        return person;
    }

    private createBasicMovie(title: string, releaseDate: string): BasicMovie {
        let basicMovie = new BasicMovie();
        basicMovie.title = title;
        basicMovie.mediaType = "Movie";
        basicMovie.releaseDate = releaseDate;
        return basicMovie;
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
