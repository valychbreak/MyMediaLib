
import {FavouriteMedia} from "../../favourites/shared/FavouriteMedia";
import {MovieService} from "../../movies/shared/movie.service";
import {Movie} from "../../movies/shared/movie";

export class User {
  id: number;
  username: string;
  name: string;
  email: string;
  private movieService: MovieService;


  constructor(id: number, username: string, name: string, email: string) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.email = email;
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
  public setMovieService(movieService: MovieService) {
    this.movieService = movieService;
  }

  get favourites() : FavouriteMedia[] {
    let f = new FavouriteMedia();
    f.date = "01/01/2016 15:30";
    f.media = new Movie();
    f.media.name = "Test 123";
    let favourites: FavouriteMedia[] = [];
    favourites.push(f);
    var movies: Movie[];

    /*let injector = ReflectiveInjector.resolveAndCreate([MovieService, Http]);
    this.movieService = injector.get(MovieService);*/

    /*this.movieService.getMovies().then(movies => {
      movies.forEach(function (movie) {
        let fav = new FavouriteMedia();
        fav.date = "01/01/2016 15:30";
        fav.media = movie;
        favourites.push(fav);
      });
    });*/
    return favourites;
  }
}
