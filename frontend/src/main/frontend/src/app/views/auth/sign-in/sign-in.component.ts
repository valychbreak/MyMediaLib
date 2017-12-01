import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {MovieDetailsModalComponent} from "../../movies/movie-details-modal/movie-details-modal.component";
import {User} from "../../../shared/users/user";
import {LoginService} from "../../../service/login.service";
import {Http} from "@angular/http";
import {Config} from "../../../config/config";

@Component({
    selector: 'app-sign-in',
    templateUrl: 'sign-in.component.html',
    styleUrls: ['sign-in.component.css']
})
export class SignInComponent implements OnInit {
    title: string;
    user: User;

    //isUserAuthenticated: boolean;

    constructor(private loginService: LoginService, private http: Http) {
    }

    ngOnInit() {
        this.title = "Sign in";

        this.user = new User();

        //this.checkAuthenticationStatus();
    }

    /*private checkAuthenticationStatus() {
      this.loginService.isAuthenticatedPromise()
          .then(data => this.isUserAuthenticated = data)
    }*/

    checkAuthentication() {
        this.http.get(Config.dataRequestLink + "/islogged/test")
            .toPromise()
            .then(response => {
                console.log("response: " + response.json());
            })
    }

    save(userModel: User, isValid: boolean) {
        console.log(userModel, isValid);

        if (isValid) {
            this.loginService.authenticate(userModel.username, userModel.password).then(user => {
                console.log("User on login: " + user.username);
                //this.checkAuthenticationStatus();
            }).catch(this.handleError);
        }
    }

    isAuthenticated() {
        return this.loginService.isAuthenticated();
    }

    logout() {
        this.loginService.logout(true);
        //this.checkAuthenticationStatus();
    }

    getLoggedUserName() {
        return this.loginService.getLoggedUsername();
    }

    private handleError(error: any) {
        console.error('An error occurred', error); // for demo purposes only
        //return Promise.reject(error.message || error);
    }

    inputHasErrors(input) {
        return input.errors && (input.dirty || input.touched)
    }

}
