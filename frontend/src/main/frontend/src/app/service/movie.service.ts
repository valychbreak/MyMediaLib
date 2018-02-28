import 'rxjs/add/operator/toPromise';
import {Injectable} from "@angular/core";
import {Movie} from "../shared/movie/movie";
import {HttpClient} from "@angular/common/http";
import {MovieSearchResult} from "../shared/search/result/movie-search-result";

@Injectable()
export class MovieService {
    constructor(private http: HttpClient) {
    }

    getMovies(): Promise<Movie[]> {
        return this.http.get<Movie[]>("http://localhost:8080/api/movie/search?s=spider-man")
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    searchMedia(searchString: string, page: number): Promise<MovieSearchResult> {
        // TODO: use standard methods of building params
        if (!page) {
            page = 1;
        }
        return this.http.get<MovieSearchResult>("http://localhost:8080/api/media/search?q=" + searchString + "&p=" + page)
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    searchMovie(searchString: string, page: number): Promise<MovieSearchResult> {
        // TODO: use standard methods of building params
        if (!page) {
            page = 1;
        }
        return this.http.get<MovieSearchResult>("http://localhost:8080/api/movie/search?q=" + searchString + "&p=" + page)
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    searchTvShow(searchString: string, page: number): Promise<MovieSearchResult> {
        // TODO: use standard methods of building params
        if (!page) {
            page = 1;
        }
        return this.http.get<MovieSearchResult>("http://localhost:8080/api/tvshow/search?q=" + searchString + "&p=" + page)
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    getMovie(id: number | string): Promise<Movie> {
        /*return this.getMovies().then(movies => movies[0]);*/
        //return tempMovies[0];
        return this.getMovies()
            .then(movies => movies.find(movie => movie.id === +id));
    }

    getMovieByImdbId(id: string): Promise<Movie> {
        return this.http.get<Movie>("http://localhost:8080/api/media/details/" + id)
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
