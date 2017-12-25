import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-new-category-view',
    templateUrl: './new-category-view.component.html',
    styleUrls: ['./new-category-view.component.css']
})
export class NewCategoryViewComponent implements OnInit {

    newCategoryForm: FormGroup;

    constructor(public activeModal: NgbActiveModal) {
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
    }

}
