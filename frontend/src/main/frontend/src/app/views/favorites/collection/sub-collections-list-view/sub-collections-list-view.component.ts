import {Component, Input, OnInit} from '@angular/core';
import {MediaCollection} from "../../../../shared/favorites/collection/media-collection";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {NewCategoryViewComponent} from "../new-collection-view/new-collection-view.component";
import {Router} from "@angular/router";
import {MdcDialog} from "@angular-mdc/web";
import {NewCollectionDialogComponent} from "../new-collection-view/new-collection-dialog.component";

@Component({
    selector: 'sub-categories-list-view',
    templateUrl: './sub-collections-list-view.component.html',
    styleUrls: ['./sub-collections-list-view.component.scss']
})
export class SubCategoriesListViewComponent implements OnInit {

    @Input()
    private currentCategory: MediaCollection;

    constructor(private modalService: NgbModal, private router: Router, private dialog: MdcDialog) {
    }

    ngOnInit() {
    }

    openDialog() {
        const dialogRef = this.dialog.open(NewCollectionDialogComponent, {
            escapeToClose: true,
            clickOutsideToClose: true
        });

        dialogRef.componentInstance.parentCategory = this.currentCategory;
    }

    goToCollection(collection: MediaCollection) {
        this.router.navigate(['/collection', collection.id]);
    }

}
