import {Component, OnInit} from '@angular/core';
import {User} from "../../../shared/users/user";
import {LoginService} from "../../../service/login.service";
import {HttpClient} from "@angular/common/http";
import {AccountEventsService} from "../../../account/account-events.service";
import {UserCredentials} from "../../../shared/users/user-credentials";
import {Account} from "../../../account/account";
import {Router} from "@angular/router";
import {RelativeNavigationLink} from "../../../config/relative-navigation-link";

@Component({
    selector: 'app-sign-in',
    templateUrl: 'sign-in.component.html',
    styleUrls: ['sign-in.component.css']
})
export class SignInComponent implements OnInit {
    title: string;
    userCredentials: UserCredentials;
    user: User;

    constructor(private loginService: LoginService,
                private http: HttpClient,
                private accountEventsService: AccountEventsService,
                private router: Router) {
    }

    ngOnInit() {
        this.title = "Sign in";

        this.userCredentials = new UserCredentials();
        this.user = null;

        this.updateUserDetails();
    }

    save(userModel: UserCredentials, isValid: boolean) {
        console.log(userModel, isValid);

        if (isValid) {
            this.loginService.authenticate(userModel.username, userModel.password).then(accessToken => {
                this.loginService.requestUser()
                    .then(user => {
                        this.accountEventsService.saveUser(user);
                        this.accountEventsService.loginSuccess(new Account());
                        this.user = user;

                        this.router.navigateByUrl(RelativeNavigationLink.MOVIES)
                    })
                    .catch(error => {
                        this.user = null;
                        return Promise.reject(error.message || error);
                    });
            }).catch(this.handleError);
        }
    }

    private updateUserDetails() {
        this.user = this.accountEventsService.getUser();
    }

    private handleError(error: any) {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }

    inputHasErrors(input) {
        return input.errors && (input.dirty || input.touched)
    }

}
