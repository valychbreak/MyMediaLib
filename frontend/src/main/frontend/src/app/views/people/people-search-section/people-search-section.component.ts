import {Component, OnInit} from '@angular/core';
import {SearchComponentSection} from "../../search-component-section";
import {PeopleService} from "../../../service/people.service";
import {Person} from "../../../shared/person/person";
import {BasicMovie} from "../../../shared/movie/basic-movie";
import {SearchResult} from "../../../shared/search/search-result";

@Component({
    selector: 'app-people-search-section',
    templateUrl: './people-search-section.component.html',
    styleUrls: ['./people-search-section.component.css']
})
export class PeopleSearchSectionComponent extends SearchComponentSection<Person> implements PageOriented, OnInit {

    constructor(private peopleService: PeopleService) {
        super();
    }

    ngOnInit() {
        //this.mockData();
    }

    private mockData() {
        this.searchResult.items = [
            this.createPerson(),
            this.createPerson(),
            this.createPerson(),
            this.createPerson()
        ];
    }

    private createPerson(): Person {
        let person = new Person();
        person.name = "Brad Pitt";
        person.isAdult = true;
        person.image = "https://image.tmdb.org/t/p/w320/ejYIW1enUcGJ9GS3Bs34mtONwWS.jpg";

        person.knownFor = [
            this.createBasicMovie("Fight club", "1996"),
            this.createBasicMovie("Spider man", "2004"),
            this.createBasicMovie("The amazing spider-man", "2013")
        ];
        return person;
    }

    private createBasicMovie(title: string, releaseDate: string): BasicMovie {
        let basicMovie = new BasicMovie();
        basicMovie.title = title;
        basicMovie.mediaType = "Movie";
        basicMovie.releaseDate = releaseDate;
        return basicMovie;
    }

    doSearch(query: string, page: number): Promise<SearchResult<Person>> {
        return this.peopleService.searchPeople(this.searchParams.query, page).then(searchResult => {
            return this.searchResult = searchResult;
        });
    }


}
