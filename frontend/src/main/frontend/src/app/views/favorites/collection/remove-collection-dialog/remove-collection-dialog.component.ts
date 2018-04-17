import {Component, Input, OnInit} from '@angular/core';
import {MdcDialogRef} from "@angular-mdc/web";
import {NewCollectionDialogComponent} from "../new-collection-view/new-collection-dialog.component";
import {MediaCollection} from "../../../../shared/favorites/collection/media-collection";
import {MediaCollectionService} from "../../../../service/media-collection.service";
import {TempDelegate} from "./temp-delegate";

@Component({
    selector: 'app-remove-collection-dialog',
    templateUrl: './remove-collection-dialog.component.html',
    styleUrls: ['./remove-collection-dialog.component.scss']
})
export class RemoveCollectionDialogComponent implements OnInit {

    @Input()
    collection: MediaCollection;

    // TODO: workaround for old MDC lib
    @Input()
    onSuccessfulRemoval: TempDelegate<MediaCollection>;

    constructor(public dialogRef: MdcDialogRef<NewCollectionDialogComponent>, private mediaCollectionService: MediaCollectionService) {
    }

    ngOnInit() {

    }

    removeCollection() {
        this.mediaCollectionService.removeCollection(this.collection)
            .then(response => {
                if (this.onSuccessfulRemoval != undefined) {
                    this.onSuccessfulRemoval(this.collection);
                }
            });
    }
}
