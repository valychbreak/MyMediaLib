import {Injectable} from "@angular/core";
import {Config} from "../config/config";
import {UserService} from "./user.service";
import {LoginService} from "./login.service";
import {Movie} from "../shared/movie/movie";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class UserFavouritesService {

    constructor(private http: HttpClient, private userService: UserService, private loginService: LoginService) {
    }

    getFavourites(): Promise<Movie[]> {
        return this.http.get<Movie[]>(Config.DATA_REQUEST_LINK + "/user/favourites")
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    addMedia(movie: Movie): Promise<Movie> {
        let username = this.loginService.getLoggedUsername();
        let headers = new HttpHeaders({'Content-Type': 'application/json'});
        return this.http.post<Movie>(Config.DATA_REQUEST_LINK + "/user/favourites/add", movie, {headers})
            .toPromise()
            .then(response => response)
    }

    removeMedia(movie: Movie): Promise<Movie> {
        let username = this.loginService.getLoggedUsername();
        let headers = new HttpHeaders({'Content-Type': 'application/json'});
        return this.http.post<Movie>(Config.DATA_REQUEST_LINK + "/user/favourites/remove", movie, {headers})
            .toPromise()
            .then(response => response)
    }

    getUserFavourites(username: string): Promise<Movie[]> {
        return this.http.get<Movie[]>(Config.DATA_REQUEST_LINK + "/user/" + username + "/favourites")
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
