import {Component, OnInit} from '@angular/core';
import {Movie} from "../../shared/movie/movie";
import {MovieService} from "../../service/movie.service";
import {Router} from "@angular/router";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {MovieDetailsModalComponent} from "./movie-details-modal/movie-details-modal.component";
import {AbstractForm} from "../../base/form";
import {Person} from "../../shared/person/person";
import {BasicMovie} from "../../shared/movie/basic-movie";
import {PeopleService} from "../../service/people.service";
import {SearchResult} from "../../shared/search/search-result";
import {SearchParams} from "../../shared/search/search-params";

@Component({
    selector: 'app-movie',
    templateUrl: 'movies.component.html',
    styleUrls: ['movies.component.css']
})
export class MoviesComponent extends AbstractForm implements OnInit {
    people: Person[];
    searchParams: SearchParams;
    mediaSearchResult: SearchResult;

    constructor(private router: Router, private movieService: MovieService, private modalService: NgbModal, private peopleService: PeopleService) {
        super()
    }

    ngOnInit() {
        this.mediaSearchResult = new SearchResult();
        this.mediaSearchResult.page = 1;
        this.mediaSearchResult.totalPages = 0;
        this.searchParams = new SearchParams();
        this.searchParams.page = 1;

        this.mockData();
    }

    private mockData() {
        this.people = [
            this.createPerson(),
            this.createPerson(),
            this.createPerson(),
            this.createPerson()
        ];
    }

    private createPerson() {
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

    submitSearch(searchParams: SearchParams, isValid: boolean): void {
        this.searchParams = new SearchParams();
        this.searchParams.query = searchParams.query;
        this.searchParams.page = searchParams.page;

        this.peopleService.searchPeople(searchParams.query).then(people => this.people = people);
    }

}
