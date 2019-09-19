import {Component, OnInit} from '@angular/core';
import {User} from "../../../shared/users/user";
import {LoginService} from "../../../service/login.service";
import {Config} from "../../../config/config";
import {HttpClient} from "@angular/common/http";
import {AccountEventsService} from "../../../account/account-events.service";
import {UserCredentials} from "../../../shared/users/user-credentials";

@Component({
    selector: 'app-sign-in',
    templateUrl: 'sign-in.component.html',
    styleUrls: ['sign-in.component.css']
})
export class SignInComponent implements OnInit {
    title: string;
    userCredentials: UserCredentials;
    user: User;

    constructor(private loginService: LoginService, private http: HttpClient, private accountEventsService: AccountEventsService) {
    }

    ngOnInit() {
        this.title = "Sign in";

        this.userCredentials = new UserCredentials();
        this.user = null;

        this.updateUserDetails();

        //this.checkAuthenticationStatus();
    }

    /*private checkAuthenticationStatus() {
      this.loginService.isAuthenticatedPromise()
          .then(data => this.isUserAuthenticated = data)
    }*/

    checkAuthentication() {
        this.http.get(Config.DATA_REQUEST_LINK + "/islogged/test")
            .toPromise()
            .then(response => {
                console.log("response: " + response);
            })
    }

    save(userModel: UserCredentials, isValid: boolean) {
        console.log(userModel, isValid);

        if (isValid) {
            this.loginService.authenticate(userModel.username, userModel.password).then(accessToken => {
                //console.log("User on login: " + user.username);
                console.log("access token: " + accessToken.access_token);
                this.loginService.requestUser()
                    .then(user => {
                        this.accountEventsService.saveUser(user);
                        this.user = user;
                    })
                    .catch(error => {
                        this.user = null;
                        return Promise.reject(error.message || error);
                    });

                //this.checkAuthenticationStatus();
            }).catch(this.handleError);
        }
    }

    private updateUserDetails() {
        this.user = this.accountEventsService.getUser();
    }

    isAuthenticated() {
        return this.user != null;
    }

    logout() {
        this.loginService.logout(true);
        //this.checkAuthenticationStatus();
    }

    getLoggedUserName() {
        return this.user == null ? "Anonymous" : this.user.username;
    }

    private handleError(error: any) {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }

    inputHasErrors(input) {
        return input.errors && (input.dirty || input.touched)
    }

}
