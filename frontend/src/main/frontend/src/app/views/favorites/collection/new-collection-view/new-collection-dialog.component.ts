import {Component, Input} from '@angular/core';
import {MdcDialogRef} from '@angular-mdc/web';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MediaCollectionService} from "../../../../service/media-collection.service";
import {MediaCollection} from "../../../../shared/favorites/collection/media-collection";

@Component({
    selector: 'app-new-category-view',
    templateUrl: './new-collection-dialog.component.html',
    styleUrls: ['./new-collection-dialog.component.scss'],
})
export class NewCollectionDialogComponent {
    @Input()
    parentCategory: MediaCollection;

    demoForm: FormGroup;
    username: string;

    constructor(public dialogRef: MdcDialogRef<NewCollectionDialogComponent>, private categoryService: MediaCollectionService) {
        this.demoForm = new FormGroup({
            collectionName: new FormControl({ value: '', disabled: false }, Validators.required)
        });
    }

    createNewCategory() {
        let collectionName = this.demoForm.value.collectionName;
        let newCategory = new MediaCollection();
        newCategory.name = collectionName;

        let parentCat = MediaCollection.copyOf(this.parentCategory);
        parentCat.subCollections = null;

        newCategory.parent = parentCat;

        this.categoryService.addNewCategory(newCategory).then(category => {
            console.log("MediaCollection returned:");
            console.log(category);

            this.parentCategory.subCollections.push(category);
            this.dialogRef.close();
        });
    }
}
