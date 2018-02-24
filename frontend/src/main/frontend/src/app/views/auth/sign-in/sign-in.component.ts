import {Component, OnInit} from '@angular/core';
import {User} from "../../../shared/users/user";
import {LoginService} from "../../../service/login.service";
import {Config} from "../../../config/config";
import {HttpClient} from "@angular/common/http";

@Component({
    selector: 'app-sign-in',
    templateUrl: 'sign-in.component.html',
    styleUrls: ['sign-in.component.css']
})
export class SignInComponent implements OnInit {
    title: string;
    user: User;

    //isUserAuthenticated: boolean;

    constructor(private loginService: LoginService, private http: HttpClient) {
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
                console.log("response: " + response);
            })
    }

    save(userModel: User, isValid: boolean) {
        console.log(userModel, isValid);

        if (isValid) {
            this.loginService.authenticate(userModel.username, userModel.password).then(accessToken => {
                //console.log("User on login: " + user.username);
                console.log("access token: " + accessToken.access_token)
                this.http.get(Config.dataRequestLink + "/principal", {/*headers: {'Authorization': 'Bearer ' + accessToken.access_token}*/})
                    .toPromise()
                    .then(principal => {
                        console.log(principal);
                    })
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
        return Promise.reject(error.message || error);
    }

    inputHasErrors(input) {
        return input.errors && (input.dirty || input.touched)
    }

}
