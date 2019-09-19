import 'rxjs/add/operator/toPromise';
import {Injectable} from "@angular/core";
import {Config} from "../config/config";
import {HttpClient, HttpParams} from "@angular/common/http";
import {PeopleSearchResult} from "../shared/search/result/people-search-result";

@Injectable()
export class PeopleService {
    static PEOPLE_URL = Config.DATA_REQUEST_LINK + "/people";


    constructor(private http: HttpClient) {
    }

    searchPeople(searchString: string, page: number): Promise<PeopleSearchResult> {
        if (!page) {
            page = 1;
        }

        let params = new HttpParams()
            .append("q", searchString)
            .append("p", page.toString());

        return this.http.get<PeopleSearchResult>(PeopleService.PEOPLE_URL + "/search", { params: params })
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}