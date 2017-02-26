import {Injectable}     from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {User} from "./user";
import {Config} from "../../config/config";

@Injectable()
export class LoginService {
    static authenticateURL = Config.dataRequestLink + "signin";


    constructor(private http: Http) {
    }

    authenticate(username: string, password: string): Promise<User> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        return this.http.post(LoginService.authenticateURL, {username, password}, options)
            .toPromise()
            .then(response => {
                let user = response.json() as User;
                console.log(user);
                localStorage.setItem("currentUsername", username);
                return
            })
            .catch(this.handleError);
    }

    logout(contactServer: boolean) {
        localStorage.removeItem("currentUsername");
    }

    isAuthenticated() {
        return !!localStorage.getItem("currentUsername")
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}