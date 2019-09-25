import {Component, OnInit} from '@angular/core';
import {User} from "../../../shared/users/user";
import {LoginService} from "../../../service/login.service";
import {HttpClient} from "@angular/common/http";
import {AccountEventsService} from "../../../account/account-events.service";
import {UserCredentials} from "../../../shared/users/user-credentials";
import {Account} from "../../../account/account";
import {Router} from "@angular/router";
import {RelativeNavigationLink} from "../../../config/relative-navigation-link";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
    selector: 'app-sign-in',
    templateUrl: 'sign-in.component.html',
    styleUrls: ['sign-in.component.css']
})
export class SignInComponent implements OnInit {
    title: string;
    signInForm: FormGroup;
    invalidCredentials: boolean;

    userCredentials: UserCredentials;
    user: User;

    constructor(private loginService: LoginService,
                private http: HttpClient,
                private accountEventsService: AccountEventsService,
                private router: Router,
                private formBuilder: FormBuilder) {

        this.createSignInForm();
    }

    ngOnInit() {
        this.title = "Sign in";

        this.userCredentials = new UserCredentials();
        this.user = null;

        this.resetErrors();
        this.updateUserDetails();

        if (this.user != null) {
            this.router.navigateByUrl(RelativeNavigationLink.MOVIES);
        }
    }

    createSignInForm() {
        this.signInForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        })
    }

    resetErrors() {
        this.invalidCredentials = false;
    }

    onSubmit() {
        let userModel = this.signInForm.value as UserCredentials;
        let isValid = this.signInForm.valid;

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
            }).catch((error) => {
                this.showInvalidCredentialsError();
            });
        }
    }

    private updateUserDetails() {
        this.user = this.accountEventsService.getUser();
    }

    private showInvalidCredentialsError() {
        this.invalidCredentials = true;
    }

    inputHasErrors(input) {
        return input.errors && (input.dirty || input.touched)
    }

}
