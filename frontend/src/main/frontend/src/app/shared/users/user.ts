import {FavouriteMedia} from "../favorites/FavouriteMedia";
import {MovieService} from "../../service/movie.service";
import {Movie} from "../movie/movie";

export class User {
    id: number;
    username: string;
    password: string;
    name: string;
    email: string;
    //private movieService: MovieService;


    constructor(/*id: number, username: string, title: string, email: string*/) {
        /*this.id = id;
        this.username = username;
        this.title = title;
        this.email = email;*/
    }

    /*get favourites() : Promise<FavouriteMedia> {
      var favourites: FavouriteMedia[];
      var movies: Movie[];


      return this.movieService.getMovies().then(movies => {
        movies.forEach(function (movie) {
          let fav = new FavouriteMedia();
          fav.date = "01/01/2016 15:30";
          fav.media = movie;
          favourites.push(fav);
        });
        return Observable.of(favourites).toPromise();
      });
    }*/
}
