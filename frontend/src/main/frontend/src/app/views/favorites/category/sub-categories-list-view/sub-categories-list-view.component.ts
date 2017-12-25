import {Component, Input, OnInit} from '@angular/core';
import {Category} from "../../../../shared/favorites/category/category";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {NewCategoryViewComponent} from "../new-category-view/new-category-view.component";

@Component({
    selector: 'sub-categories-list-view',
    templateUrl: './sub-categories-list-view.component.html',
    styleUrls: ['./sub-categories-list-view.component.css']
})
export class SubCategoriesListViewComponent implements OnInit {

    @Input()
    private currentCategory: Category;

    constructor(private modalService: NgbModal) {
    }

    ngOnInit() {
    }

    openNewCategoryModalWindow() {
        this.modalService.open(NewCategoryViewComponent)
    }

}
