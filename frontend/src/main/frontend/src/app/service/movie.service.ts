import 'rxjs/add/operator/toPromise';
import {Injectable} from "@angular/core";
import {Movie} from "../shared/movie/movie";
import {HttpClient, HttpParams} from "@angular/common/http";
import {MovieSearchResult} from "../shared/search/result/movie-search-result";

@Injectable()
export class MovieService {
    constructor(private http: HttpClient) {
    }

    searchMedia(searchString: string, page: number): Promise<MovieSearchResult> {
        if (!page) {
            page = 1;
        }

        let params = new HttpParams()
            .append("q", searchString)
            .append("p", page.toString());

        return this.http.get<MovieSearchResult>("http://localhost:8080/api/media/search", { params: params })
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    searchMovie(searchString: string, page: number): Promise<MovieSearchResult> {
        if (!page) {
            page = 1;
        }

        let params = new HttpParams()
            .append("q", searchString)
            .append("p", page.toString());

        return this.http.get<MovieSearchResult>("http://localhost:8080/api/movie/search", { params: params })
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    searchTvShow(searchString: string, page: number): Promise<MovieSearchResult> {
        if (!page) {
            page = 1;
        }

        let params = new HttpParams()
            .append("q", searchString)
            .append("p", page.toString());

        return this.http.get<MovieSearchResult>("http://localhost:8080/api/tvshow/search", { params: params })
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
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
