import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {AccessToken} from "../shared/AccessToken";
import {User} from "../shared/users/user";
import {Account} from "./account";

@Injectable()
export class AccountEventsService extends Subject<any> {

    static ACCESS_TOKEN_PATH = "ACCESS-TOKEN-OBJECT";
    static LOGGED_USER_TOKEN = "LOGGED-USER-OBJECT";

    authenticated: boolean;

    constructor() {
        super();
        this.authenticated = false;
    }

    loginSuccess(account: Account) {
        if (account) {
            account.authenticated = true;
            super.next(account);
        }
        this.authenticated = true;
    }

    logout(account: Account) {
        this.clearToken();

        if (account) {
            account.authenticated = false;
            super.next(account);
        }
        this.authenticated = false;
    }

    saveUser(user: User) {
        localStorage.setItem(AccountEventsService.LOGGED_USER_TOKEN, JSON.stringify(user));
    }

    getUser(): User {
        return JSON.parse(localStorage.getItem(AccountEventsService.LOGGED_USER_TOKEN));
    }

    saveToken(accessToken: AccessToken) {
        localStorage.setItem(AccountEventsService.ACCESS_TOKEN_PATH, JSON.stringify(accessToken));
    }

    clearToken(): boolean {
        localStorage.removeItem(AccountEventsService.ACCESS_TOKEN_PATH);
        localStorage.removeItem(AccountEventsService.LOGGED_USER_TOKEN);
        return true;
    }

    getToken(): AccessToken {
        return JSON.parse(localStorage.getItem(AccountEventsService.ACCESS_TOKEN_PATH)) as AccessToken;
    }
}