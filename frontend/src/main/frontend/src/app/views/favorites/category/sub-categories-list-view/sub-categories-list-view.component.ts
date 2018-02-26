import {Component, Input, OnInit} from '@angular/core';
import {MediaCollection} from "../../../../shared/favorites/collection/media-collection";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {NewCategoryViewComponent} from "../new-category-view/new-category-view.component";

@Component({
    selector: 'sub-categories-list-view',
    templateUrl: './sub-categories-list-view.component.html',
    styleUrls: ['./sub-categories-list-view.component.css']
})
export class SubCategoriesListViewComponent implements OnInit {

    @Input()
    private currentCategory: MediaCollection;

    constructor(private modalService: NgbModal) {
    }

    ngOnInit() {
    }

    openNewCategoryModalWindow() {
        const modal = this.modalService.open(NewCategoryViewComponent);
        modal.componentInstance.parentCategory = this.currentCategory;
        //this.modalService.open(NewCategoryViewComponent)
    }

}
