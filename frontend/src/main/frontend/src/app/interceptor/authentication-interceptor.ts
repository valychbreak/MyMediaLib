
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {AccountEventsService} from "../account/account-events.service";
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw'
import {LoginService} from "../service/login.service";
import {AccessToken} from "../shared/AccessToken";

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
            modifiedRequest = req.clone({setHeaders: {'Authorization': 'Bearer ' + accessToken.access_token/*, 'AuthTest': 'myValue123'*/}});
            //modifiedRequest.headers.set("Authorization", 'Bearer ' + accessToken.access_token);
        } else {
            modifiedRequest = req.clone();
        }
        //modifiedRequest.headers.set('Authorization', 'Bearer ' + accessToken.access_token);
        return next.handle(modifiedRequest)
            .catch((error, caught) => {
            console.log(error);
            console.log(caught);
            if (error.status === 403) {
                console.log('Unauthorized request:', error.message);
                this.accountEventsService.logout({error: "Not authenticated"});
                //.accountEventsService.logout({error:res.text()});
            }

            if (error.status == 401) {
                if (error.error.error == "invalid_token") {
                    this.accountEventsService.clearToken();
                    console.log("Token cleared");
                }
            }
            return Observable.throw(error);
        }) as any;
        /*return next.handle(modifiedRequest)
            .catch((error, caught) => {
//intercept the respons error and displace it to the console
                console.log("Error Occurred");
                console.log(error);
//return the error to the method that called it
                return Observable.throw(error);
            }) as any;*/
    }
}