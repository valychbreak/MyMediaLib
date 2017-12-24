import {Component, Input, OnInit} from '@angular/core';
import {Category} from "../../../../shared/favorites/category/category";

@Component({
    selector: 'sub-categories-list-view',
    templateUrl: './sub-categories-list-view.component.html',
    styleUrls: ['./sub-categories-list-view.component.css']
})
export class SubCategoriesListViewComponent implements OnInit {

    @Input()
    private currentCategory: Category;

    constructor() {
    }

    ngOnInit() {
    }

}
