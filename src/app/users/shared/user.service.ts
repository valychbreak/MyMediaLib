import {Injectable}     from '@angular/core';
import {Http} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {User} from "./user";
import {Config} from "../../config/config";

@Injectable()
export class UserService {
  static usersURL = Config.dataRequestLink + "users";


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

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
