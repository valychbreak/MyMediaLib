import {Component, OnInit, Input} from '@angular/core';
import {Person} from "../../../shared/person/person";

@Component({
    selector: 'person-short-view',
    templateUrl: './person-short-view.component.html',
    styleUrls: ['./person-short-view.component.css']
})
export class PersonShortViewComponent implements OnInit {

    @Input()
    person: Person;

    constructor() {
    }

    ngOnInit() {
    }


}
