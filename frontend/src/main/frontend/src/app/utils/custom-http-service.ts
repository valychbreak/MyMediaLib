
import {Injectable} from "@angular/core";
import {Http, RequestOptions, RequestOptionsArgs, Response, XHRBackend} from "@angular/http";
import {Observable, Observer} from "rxjs";
import {LoginService} from "../users/shared/login.service";
import {AccountEventsService} from "../account/account-events.service";
@Injectable()
export class CustomHttpService extends Http {
    constructor(backend: XHRBackend, options: RequestOptions, private accountEventsService: AccountEventsService) {
        super(backend, options);
    }

    get(url: string, options?: RequestOptionsArgs): Observable<Response> {
        return Observable.create((observer:Observer<Response>) => {
            super.get(url, options)
                .subscribe((res:Response) => {
                    this.mapResponse(res,observer);
                },(res:Response) => {
                    this.catchResponse(res,observer);
                });
        });
    }
    post(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        return Observable.create((observer:Observer<Response>) => {
            super.post(url,body,options)
                .subscribe((res:Response) => {
                    this.mapResponse(res,observer);
                },(res:Response) => {
                    this.catchResponse(res,observer);
                });
        });
    }
    put(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        return Observable.create((observer:Observer<Response>) => {
            super.put(url,body,options)
                .subscribe((res:Response) => {
                    this.mapResponse(res,observer);
                },(res:Response) => {
                    this.catchResponse(res,observer);
                });
        });
    }

    mapResponse(res:Response,observer:Observer<Response>):void {
        observer.next(res);
        observer.complete();
    }
    catchResponse(res:Response,observer:Observer<Response>):void {
        if(res.status === 403) {
            console.log('Unauthorized request:',res.text());
            this.accountEventsService.logout({error: "Not authenticated"});
            //.accountEventsService.logout({error:res.text()});
        }
        observer.complete();
    }
}