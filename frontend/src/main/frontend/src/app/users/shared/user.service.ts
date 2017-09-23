import {Injectable}     from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {User} from "./user";
import {Config} from "../../config/config";
import {Movie} from "../../movies/shared/movie";

@Injectable()
export class UserService {
  static usersURL = Config.dataRequestLink + "/users";
  static createUserURL = Config.dataRequestLink + "/user/add";


  constructor(private http: Http) {
  }

  getUsers(): Promise<User[]> {
    return this.http.get(UserService.usersURL) //"http://localhost:4200/app/data/users.json"
      .toPromise()
      .then(response => response.json() as User[])
      .catch(this.handleError);
  }

  getUser(id: number | string) {
    return this.getUsers()
      .then(users => users.find(user => user.id === +id));
  }

  addUser(user: User): Promise<User> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(UserService.createUserURL, user, options)
        .toPromise()
        .then(response => response.json() as User)
  }

  getUserFavourites(username: string): Promise<Movie[]> {
    return this.http.get(Config.dataRequestLink + "/user/favourites")
        .toPromise()
        .then(response => response.json() as Movie[])
        .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
