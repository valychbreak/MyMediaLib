import {Component, OnInit} from '@angular/core';
import {AbstractForm} from "../../../base/form";
import {User} from "../../../shared/users/user";
import {UserService} from "../../../service/user.service";
import {Router} from "@angular/router";
import {RelativeNavigationLink} from "../../../config/relative-navigation-link";

@Component({
    selector: 'app-sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent extends AbstractForm implements OnInit {

    user: User;

    constructor(private userService: UserService, private router: Router) {
        super();
    }

    ngOnInit() {
        this.user = new User();
    }

    save(userModel: User, isValid: boolean) {
        console.log(userModel, isValid);
        if (isValid) {
            this.userService.addUser(userModel)
                .then(user => {
                    console.log("User was registered successfully");

                    this.router.navigateByUrl(RelativeNavigationLink.SIGN_IN);
                })
        }
    }
}
