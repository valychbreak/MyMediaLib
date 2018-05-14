import {Injectable} from '@angular/core';

import 'rxjs/add/operator/toPromise';
import {User} from "../shared/users/user";
import {Config} from "../config/config";
import {AccountEventsService} from "../account/account-events.service";
import {Account} from "../account/account";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AccessToken} from "../shared/AccessToken";

@Injectable()
export class LoginService {
    static signinURL = Config.hostLink + "/api/signin";
    static logoutURL = Config.dataRequestLink + "/logout";

    static LOGGED_USER_KEY = "loggedUserKey";


    constructor(private http: HttpClient, private accountEventsService: AccountEventsService) {
    }

    authenticate(username: string, password: string): Promise<AccessToken> {
        let headers = new HttpHeaders({'Content-Type': 'application/json'});
        return this.http.post<AccessToken>(LoginService.signinURL, {"username" : username, "password": password}, {headers})
            .toPromise()
            .then(response => {
                let accessToken = response as AccessToken;
                console.log(accessToken);
                this.accountEventsService.saveToken(accessToken);
                return accessToken;
            })
            .catch(this.onLoginFail);
    }

    logout(contactServer: boolean) {
        if (contactServer) {
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

    requestUser(): Promise<any> {
        return this.http.get<any>(Config.dataRequestLink + "/principal",{/*responseType: 'text'*/})
            .toPromise()
            .then(response => response)
    }

    getLoggedUsername() {
        if (this.isAuthenticated()) {
            return (JSON.parse(localStorage.getItem(LoginService.LOGGED_USER_KEY)) as User).username;
        } else {
            return "";
        }
    }

    private onLoginFail(error: any): Promise<any> {
        return Promise.reject("Wrong credentials");
    }
}