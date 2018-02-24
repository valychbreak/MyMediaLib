import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {AccessToken} from "../shared/AccessToken";

@Injectable()
export class AccountEventsService extends Subject<any> {

    static ACCESS_TOKEN_PATH = "ACCESS-TOKEN-OBJECT";

    authenticated: boolean;

    constructor() {
        super();
        this.authenticated = false;
    }

    loginSuccess(account: any) {
        if (account) {
            account.authenticated = true;
            super.next(account);
        }
        this.authenticated = true;
    }

    logout(account: any) {
        if (account) {
            account.authenticated = false;
            super.next(account);
        }
        this.authenticated = false;
    }

    saveToken(accessToken: AccessToken) {
        localStorage.setItem(AccountEventsService.ACCESS_TOKEN_PATH, JSON.stringify(accessToken));
    }

    clearToken(): boolean {
        localStorage.removeItem(AccountEventsService.ACCESS_TOKEN_PATH);
        return true;
    }

    getToken(): AccessToken {
        return JSON.parse(localStorage.getItem(AccountEventsService.ACCESS_TOKEN_PATH)) as AccessToken;
    }
}