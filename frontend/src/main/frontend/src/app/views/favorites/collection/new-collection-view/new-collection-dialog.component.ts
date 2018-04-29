import {Component} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material";

@Component({
    selector: 'app-new-category-view',
    templateUrl: './new-collection-dialog.component.html',
    styleUrls: ['./new-collection-dialog.component.scss'],
})
export class NewCollectionDialogComponent {
    collectionName = new FormControl('', [Validators.required]);

    constructor(public dialogRef: MatDialogRef<NewCollectionDialogComponent>) { }
}
