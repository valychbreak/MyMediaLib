import {Component, OnInit, Input} from '@angular/core';
import {Person} from "../../../shared/person/person";
import {ImageUtils} from "../../utils/image-utils";

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

    getImage(imagePath: string): string {
        return ImageUtils.getNoImageIfEmptyOrNull(imagePath);
    }

}
