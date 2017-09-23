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
import {LoginService} from "../users/shared/login.service";
import {UserFavouritesService} from "../users/shared/user-favourites.service";

@Component({
  selector: 'app-favourites',
  templateUrl: './favourites.component.html',
  styleUrls: ['./favourites.component.css']
})
export class FavouritesComponent implements OnInit {
  user: User = new User();
  fav: FavouriteMedia[];
  favouriteMedia: Movie[];

  constructor(private http: Http, private router: Router, private userFavouritesService: UserFavouritesService,
              private userService: UserService, private loginService: LoginService) { }

  ngOnInit() {
    //this.getLoggedUser();
    //this.getFavouriteMedia().subscribe(media => this.fav = media);

    this.getFavourites();
  }

  getFavouriteMedia() : Observable<FavouriteMedia[]> {
    return this.http.get("http://localhost:4200/app/data/favourites.json")
      .map((res:Response)=> res.json())
  }

  getFavourites() {
    this.userFavouritesService.getFavourites()
        .then(media => this.favouriteMedia = media);
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
