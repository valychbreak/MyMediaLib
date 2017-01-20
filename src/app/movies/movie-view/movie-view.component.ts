import { Component, OnInit } from '@angular/core';
import {Movie} from "../shared/movie";
import {MovieService} from "../shared/movie.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-movie-view',
  templateUrl: 'movie-view.component.html',
  styleUrls: ['movie-view.component.css']
})
export class MovieViewComponent implements OnInit {
  movie = new Movie();

  constructor(private movieService: MovieService, private route: ActivatedRoute) { }

  ngOnInit() {
    let id = +this.route.snapshot.params['id'];
    if(!isNaN(id)) {
      console.log(id);
      this.movieService.getMovie(id)
        .then((movie: Movie) => this.movie = movie);
    }
  }

}
