import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {MediaCollection} from "../../../../shared/favorites/collection/media-collection";
import {MediaCollectionService} from "../../../../service/media-collection.service";

@Component({
    selector: 'app-new-category-view',
    templateUrl: './new-category-view.component.html',
    styleUrls: ['./new-category-view.component.css']
})
export class NewCategoryViewComponent implements OnInit {

    @Input()
    parentCategory: MediaCollection;

    newCategoryForm: FormGroup;

    constructor(public activeModal: NgbActiveModal, private categoryService: MediaCollectionService) {
    }

    ngOnInit() {
        this.newCategoryForm = new FormGroup({
            name: new FormControl('', [
                Validators.required
            ])
        });
    }

    createCategory(formValue: any) {
        console.log(formValue.name);
        console.log("Saved to " + this.parentCategory.id + " category");

        let newCategory = new MediaCollection();
        newCategory.name = formValue.name;

        let parentCat = MediaCollection.copyOf(this.parentCategory);
        parentCat.subCollections = null;

        newCategory.parent = parentCat;

        this.categoryService.addNewCategory(newCategory).then(category => {
            console.log("MediaCollection returned:");
            console.log(category);

            this.parentCategory.subCollections.push(category);

            this.activeModal.close();
        });
    }

}
