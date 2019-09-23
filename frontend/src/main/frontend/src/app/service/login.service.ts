import {Injectable} from '@angular/core';

import 'rxjs/add/operator/toPromise';
import {User} from "../shared/users/user";
import {Config} from "../config/config";
import {AccountEventsService} from "../account/account-events.service";
import {Account} from "../account/account";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AccessToken} from "../shared/AccessToken";

@Injectable()
export class LoginService {
    static SIGN_IN_URL = Config.HOST_LINK + "/oauth/token";
    static LOGOUT_URL = Config.DATA_REQUEST_LINK + "/logout";
    static USER_DATA_LINK = Config.DATA_REQUEST_LINK + "/principal";

    static LOGGED_USER_KEY = "loggedUserKey";


    constructor(private http: HttpClient, private accountEventsService: AccountEventsService) {
    }

    authenticate(username: string, password: string): Promise<AccessToken> {
        let headers = new HttpHeaders({
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Basic ' + btoa("gigy:secret")
        });

        let httpOptions = {headers: headers};

        let urlSearchParams = new URLSearchParams();
        urlSearchParams.append("grant_type", "password");
        urlSearchParams.append("username", username);
        urlSearchParams.append("password", password);

        return this.http.post<AccessToken>(LoginService.SIGN_IN_URL, urlSearchParams.toString(), httpOptions)
            .toPromise()
            .then(response => {
                let accessToken = response as AccessToken;
                this.accountEventsService.saveToken(accessToken);
                return accessToken;
            })
            .catch(this.onLoginFail);
    }

    logout(contactServer: boolean) {
        if (contactServer) {
            this.http.get(LoginService.LOGOUT_URL)
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
        return this.http.get<any>(LoginService.USER_DATA_LINK,{/*responseType: 'text'*/})
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