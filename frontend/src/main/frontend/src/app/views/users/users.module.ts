import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UsersComponent} from './users.component';
import {UserService} from "../../service/user.service";
import {UserFormModule} from "./user-form/user-form.module";
import {FormsModule} from "@angular/forms";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        UserFormModule
    ],
    providers: [UserService],
    declarations: [UsersComponent]
})
export class UsersModule {
}
