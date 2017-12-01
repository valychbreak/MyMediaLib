import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserFormComponent} from "./user-form.component";
import {FormsModule} from "@angular/forms";


@NgModule({
    imports: [
        CommonModule,
        FormsModule
    ],
    providers: [],
    declarations: [UserFormComponent]
})
export class UserFormModule {
}
