import { Component, OnInit } from '@angular/core';
import {UserService} from "../users/shared/user.service";
import {User} from "../users/shared/user";
import {MovieService} from "../movies/shared/movie.service";
import {FavouriteMedia} from "./shared/FavouriteMedia";
import {Observable} from "rxjs";
import 'rxjs/add/operator/map'
import {Http, Response} from "@angular/http";
import {Router} from "@angular/router";
import {Movie} from "../movies/shared/movie";

@Component({
  selector: 'app-favourites',
  templateUrl: './favourites.component.html',
  styleUrls: ['./favourites.component.css']
})
export class FavouritesComponent implements OnInit {
  user: User = new User(0, null, null, null);
  fav: FavouriteMedia[];
  title: string;

  constructor(private http: Http, private router: Router, private userService: UserService, private movieService: MovieService) { }

  ngOnInit() {
    this.title = "Observable test";
    //this.getLoggedUser();
    this.getFavouriteMedia().subscribe(media => this.fav = media);
  }

  getFavouriteMedia() : Observable<FavouriteMedia[]> {
    return this.http.get("http://localhost:4200/app/data/favourites.json")
      .map((res:Response)=> res.json())
  }

  getLoggedUser() {
    this.userService.getUser(1).then((user: User) => {
      this.user = user;
      this.fav = this.user.favourites;
      console.log(this.fav.pop());
    });
  }

  onSelectMovie(movie: Movie) {
    this.router.navigate(['/movie', movie.id]);
  }

}
