import {Injectable}     from '@angular/core';
import {Http, Headers, RequestOptions, URLSearchParams} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import {User} from "./user";
import {Config} from "../../config/config";
import {Observable} from "rxjs";
import {AccountEventsService} from "../../account/account-events.service";
import {Account} from "../../account/account";

@Injectable()
export class LoginService {
    static authenticateURL = Config.dataRequestLink + "signin";
    static isLoggedURL = Config.dataRequestLink + "islogged";
    static logoutURL = Config.dataRequestLink + "logout123";

    static LOGGED_USER_KEY = "loggedUserKey";


    constructor(private http: Http, private accountEventsService: AccountEventsService) {
    }

    authenticate(username: string, password: string): Promise<User> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });
        let body = new URLSearchParams();
        body.append('username', username);
        body.append('password', password);
        return this.http.post(LoginService.authenticateURL, {username, password}, options)
            .toPromise()
            .then(response => {
                console.log("Raw response: " + response);
                console.log("Sign in response: " + response.json());
                let user = response.json() as User;
                console.log(user);
                localStorage.setItem(LoginService.LOGGED_USER_KEY, JSON.stringify(user));

                let account = new Account();
                account.login = user.username;
                this.accountEventsService.loginSuccess(account);
                return user;
            })
            .catch(this.onLoginFail);
    }

    logout(contactServer: boolean) {
        localStorage.removeItem(LoginService.LOGGED_USER_KEY);

        if(contactServer) {
            this.http.get(LoginService.logoutURL)
                .toPromise().then(response => {
                    console.log("logged out");
                    this.accountEventsService.logout(new Account());
            });
        }
    }

    isAuthenticated() {
        return !!localStorage.getItem(LoginService.LOGGED_USER_KEY);
    }

    checkIsAuthenticatedOnServer() : Promise<boolean> {
        return this.http.get(LoginService.isLoggedURL)
            .toPromise().then(response => response.text() == "true")
    }

    getLoggedUsername() {
        if(this.isAuthenticated()) {
            return (JSON.parse(localStorage.getItem(LoginService.LOGGED_USER_KEY)) as User).username;
        } else {
            return "";
        }
    }

    private onLoginFail(error: any): Promise<any> {
        return Promise.reject("Wrong credentials");
    }
}