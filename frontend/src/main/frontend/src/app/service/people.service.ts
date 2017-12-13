import 'rxjs/add/operator/toPromise';
import {Injectable} from "@angular/core";
import {Person} from "../shared/person/person";
import {Config} from "../config/config";
import {HttpClient} from "@angular/common/http";
import {SearchResult} from "../shared/search/search-result";

@Injectable()
export class PeopleService {
    static peopleURL = Config.dataRequestLink + "/people";


    constructor(private http: HttpClient) {
    }

    searchPeople(searchString: string, page: number): Promise<SearchResult> {
        if (!page) {
            page = 1;
        }
        return this.http.get<SearchResult>(PeopleService.peopleURL + "/search?q=" + searchString + "&p=" + page)
            .toPromise()
            .then(response => response as SearchResult)
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}