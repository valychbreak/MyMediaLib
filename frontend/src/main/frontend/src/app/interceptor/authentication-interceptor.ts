import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {AccountEventsService} from "../account/account-events.service";
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw'
import {Account} from "../account/account";

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

    constructor(private accountEventsService: AccountEventsService) {

    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log("Request....");
        console.log(req.body);

        let accessToken = this.accountEventsService.getToken();
        let modifiedRequest;
        if (accessToken != null) {
            modifiedRequest = req.clone({setHeaders: {'Authorization': 'Bearer ' + accessToken.access_token}});
        } else {
            modifiedRequest = req.clone();
        }

        return next.handle(modifiedRequest)
            .catch((error, caught) => {
            console.log(error);
            console.log(caught);
            if (error.status === 403) {
                console.log('Unauthorized request:', error.message);
                // this.accountEventsService.logout(new Account());
            }

            if (error.status == 401) {
                if (error.error.error == "invalid_token") {
                    this.accountEventsService.clearToken();
                    this.accountEventsService.logout(new Account());

                    console.log("Token cleared");
                }
            }
            return Observable.throw(error);
        }) as any;
    }
}