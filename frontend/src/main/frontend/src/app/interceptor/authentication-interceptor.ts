
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {AccountEventsService} from "../account/account-events.service";
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw'

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

    constructor(private accountEventsService: AccountEventsService) {

    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log("Request....");
        console.log(req.body);

        const modifiedRequest = req.clone(/*{setHeaders: {'AuthTest': 'myValue123'}}*/);
        return next.handle(modifiedRequest)
            .catch((error, caught) => {
            console.log(error);
            console.log(caught);
            if (error.status === 403) {
                console.log('Unauthorized request:', error.message);
                this.accountEventsService.logout({error: "Not authenticated"});
                //.accountEventsService.logout({error:res.text()});
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