import {Component, Inject, OnInit} from '@angular/core';
import {NewCollectionDialogComponent} from "../new-collection-view/new-collection-dialog.component";
import {MediaCollection} from "../../../../shared/favorites/collection/media-collection";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
    selector: 'app-remove-collection-dialog',
    templateUrl: './remove-collection-dialog.component.html',
    styleUrls: ['./remove-collection-dialog.component.scss']
})
export class RemoveCollectionDialogComponent implements OnInit {

    collection: MediaCollection;

    constructor(public dialogRef: MatDialogRef<NewCollectionDialogComponent>,
                @Inject(MAT_DIALOG_DATA) data: any) {
        this.collection = data.collection as MediaCollection;
    }

    ngOnInit() {

    }
}
