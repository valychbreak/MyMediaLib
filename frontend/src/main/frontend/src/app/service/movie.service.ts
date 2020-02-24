import 'rxjs/add/operator/toPromise';
import {Config} from "../config/config";
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
            .append("p", page.toString())
            .append("media-type", "media");

        return this.http.get<MovieSearchResult>(Config.DATA_REQUEST_LINK + "/media/search", { params: params })
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
            .append("p", page.toString())
            .append("media-type", "movie");

        return this.http.get<MovieSearchResult>(Config.DATA_REQUEST_LINK + "/media/search", { params: params })
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
            .append("p", page.toString())
            .append("media-type", "tvshow");

        return this.http.get<MovieSearchResult>(Config.DATA_REQUEST_LINK + "/media/search", { params: params })
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    getMovieByImdbId(id: string): Promise<Movie> {
        return this.http.get<Movie>(Config.DATA_REQUEST_LINK + "/media/details/" + id)
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
