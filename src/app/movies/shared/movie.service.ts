import 'rxjs/add/operator/toPromise';
import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Movie} from "./movie";

@Injectable()
export class MovieService {
  constructor(private http: Http) {
  }

  getMovies(): Promise<Movie[]> {
    return this.http.get("http://localhost:4200/app/data/movies.json")
      .toPromise()
      .then(response => response.json() as Movie[])
      .catch(this.handleError);
  }

  getMovie(id: number | string) : Promise<Movie> {
    /*return this.getMovies().then(movies => movies[0]);*/
    //return tempMovies[0];
    return this.getMovies()
      .then(movies => movies.find(movie => movie.id === +id));
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
