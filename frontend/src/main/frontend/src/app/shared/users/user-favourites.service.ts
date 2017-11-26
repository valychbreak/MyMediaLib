import {Injectable} from "@angular/core";
import {Config} from "../../config/config";
import {Http, Headers, RequestOptions} from "@angular/http";
import {User} from "./user";
import {UserService} from "./user.service";
import {LoginService} from "./login.service";
import {Movie} from "../movie/movie";
@Injectable()
export class UserFavouritesService {
    static usersURL = Config.dataRequestLink + "/users";
    static addMedia = Config.dataRequestLink + "/user/add";


    constructor(private http: Http, private userService: UserService, private loginService: LoginService) {
    }

    getFavourites(): Promise<Movie[]> {
        return this.http.get(Config.dataRequestLink + "/user/favourites")
            .toPromise()
            .then(response => response.json() as Movie[])
            .catch(this.handleError);
    }

    addMedia(movie: Movie): Promise<Movie> {
        let username = this.loginService.getLoggedUsername();
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return this.http.post(Config.dataRequestLink + "/user/favourites/add", movie, options)
            .toPromise()
            .then(response => response.json() as Movie)
    }

    removeMedia(movie: Movie): Promise<Movie> {
        let username = this.loginService.getLoggedUsername();
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return this.http.post(Config.dataRequestLink + "/user/favourites/remove", movie, options)
            .toPromise()
            .then(response => response.json() as Movie)
    }

    getUserFavourites(username: string): Promise<Movie[]> {
        return this.http.get(Config.dataRequestLink + "/user/" + username + "/favourites")
            .toPromise()
            .then(response => response.json() as Movie[])
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
