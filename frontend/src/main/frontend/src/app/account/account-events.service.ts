import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
@Injectable()
export class AccountEventsService extends Subject<any> {
    authenticated: boolean;

    constructor() {
        super();
        this.authenticated = false;
    }
    loginSuccess(account:any) {
        if(account) {
            account.authenticated = true;
            super.next(account);
        }
        this.authenticated = true;
    }
    logout(account:any) {
        if(account) {
            account.authenticated = false;
            super.next(account);
        }
        this.authenticated = false;
    }
}