import 'rxjs/add/operator/toPromise';
import {Injectable} from "@angular/core";
import {Config} from "../config/config";
import {HttpClient} from "@angular/common/http";
import {PeopleSearchResult} from "../shared/search/result/people-search-result";

@Injectable()
export class PeopleService {
    static peopleURL = Config.dataRequestLink + "/people";


    constructor(private http: HttpClient) {
    }

    searchPeople(searchString: string, page: number): Promise<PeopleSearchResult> {
        if (!page) {
            page = 1;
        }
        return this.http.get<PeopleSearchResult>(PeopleService.peopleURL + "/search?q=" + searchString + "&p=" + page)
            .toPromise()
            .then(response => response)
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}