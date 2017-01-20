import {Injectable}     from '@angular/core';
import {Http} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {Movie} from "../../movies/shared/movie";

@Injectable()
export class FavouriteMediaService {
  constructor(private http: Http) {
  }

  getUsers(): Promise<Movie[]> {
    return this.http.get("http://localhost:4200/app/data/users.json")
      .toPromise()
      .then(response => response.json() as Movie[])
      .catch(this.handleError);
  }

  getUser(id: number | string) {
    return this.getUsers()
      .then(users => users.find(user => user.id === +id));
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
