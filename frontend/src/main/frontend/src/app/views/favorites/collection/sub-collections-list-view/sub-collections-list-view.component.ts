import {Component, Input, OnInit} from '@angular/core';
import {MediaCollection} from "../../../../shared/favorites/collection/media-collection";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";
import {NewCollectionDialogComponent} from "../new-collection-view/new-collection-dialog.component";
import {RemoveCollectionDialogComponent} from "../remove-collection-dialog/remove-collection-dialog.component";
import {MatDialog} from "@angular/material";
import {MediaCollectionService} from "../../../../service/media-collection.service";

@Component({
    selector: 'sub-categories-list-view',
    templateUrl: './sub-collections-list-view.component.html',
    styleUrls: ['./sub-collections-list-view.component.scss']
})
export class SubCategoriesListViewComponent implements OnInit {

    @Input()
    private currentCategory: MediaCollection;

    constructor(private modalService: NgbModal, private router: Router, private dialog: MatDialog,
                private mediaCollectionService: MediaCollectionService) {
    }

    ngOnInit() {
    }

    openNewCollectionDialog() {
        const dialogRef = this.dialog.open(NewCollectionDialogComponent, {
            data: {collection: this.currentCategory}
        });

        dialogRef.afterClosed().toPromise()
            .then(newCollectionName => this.createNewCollection(newCollectionName));
    }

    confirmCollectionRemoval(collection: MediaCollection) {
        const dialogRef = this.dialog.open(RemoveCollectionDialogComponent, {
            data: {collection: collection}
        });

        dialogRef.afterClosed().toPromise()
            .then(collectionToRemove => this.removeCollection(collectionToRemove));
    }

    goToCollection(collection: MediaCollection) {
        this.router.navigate(['/collection', collection.id]);
    }

    private createNewCollection(result) {
        if (result != "") {
            let collectionName = result as string;
            let newCategory = new MediaCollection();
            newCategory.name = collectionName;

            this.mediaCollectionService.addNewCategory(newCategory).then(category => {
                console.log("MediaCollection returned:");
                console.log(category);

                this.currentCategory.subCollections.push(category);
            });
        }
    }

    private removeCollection(result) {
        if (result != "") {
            let collectionToRemove = result as MediaCollection;
            this.mediaCollectionService.removeCollection(collectionToRemove)
                .then(() => {
                    console.log("Removed collection from UI " + collectionToRemove.id);
                    let index: number = this.currentCategory.subCollections.indexOf(collectionToRemove);
                    this.currentCategory.subCollections.splice(index, 1);
                });
        }
    }
}
