import {Injectable} from '@angular/core';

import 'rxjs/add/operator/toPromise';
import {User} from "../shared/users/user";
import {Config} from "../config/config";
import {Movie} from "../shared/movie/movie";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class UserService {
    static USERS_URL = Config.DATA_REQUEST_LINK + "/users";
    static CREATE_USER_URL = Config.DATA_REQUEST_LINK + "/user/add";


    constructor(private http: HttpClient) { }

    getUsers(): Promise<User[]> {
        return this.http.get<User[]>(UserService.USERS_URL)
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    getUser(id: number | string) {
        return this.getUsers()
            .then(users => users.find(user => user.id === +id));
    }

    addUser(user: User): Promise<User> {
        let headers = new HttpHeaders({'Content-Type': 'application/json'});
        return this.http.post<User>(UserService.CREATE_USER_URL, user, {headers})
            .toPromise()
            .then(response => response)
    }

    getUserFavourites(username: string): Promise<Movie[]> {
        return this.http.get<Movie[]>(Config.DATA_REQUEST_LINK + "/user/favourites")
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
