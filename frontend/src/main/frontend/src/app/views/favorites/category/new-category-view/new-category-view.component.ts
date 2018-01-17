import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Category} from "../../../../shared/favorites/category/category";
import {CategoryService} from "../../../../service/category.service";

@Component({
    selector: 'app-new-category-view',
    templateUrl: './new-category-view.component.html',
    styleUrls: ['./new-category-view.component.css']
})
export class NewCategoryViewComponent implements OnInit {

    @Input()
    parentCategory: Category;

    newCategoryForm: FormGroup;

    constructor(public activeModal: NgbActiveModal, private categoryService: CategoryService) {
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

        let newCategory = new Category();
        newCategory.name = formValue.name;

        let parentCat = Category.copyOf(this.parentCategory);
        parentCat.subCategories = null;

        newCategory.parent = parentCat;

        this.categoryService.addNewCategory(newCategory).then(category => {
            console.log("Category returned:");
            console.log(category);
        });
    }

}
