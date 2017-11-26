import 'rxjs/add/operator/toPromise';
import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Person} from "../shared/person/person";
import {Config} from "../config/config";

@Injectable()
export class PeopleService {
    static peopleURL = Config.dataRequestLink + "/people";


    constructor(private http: Http) {
    }

    searchPeople(searchString: string): Promise<Person[]> {
        return this.http.get(PeopleService.peopleURL + "/search?q=" + searchString + "&p=1")
            .toPromise()
            .then(response => response.json() as Person[])
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}