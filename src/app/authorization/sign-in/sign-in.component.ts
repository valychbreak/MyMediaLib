import { Component, OnInit } from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {MovieDetailsModalComponent} from "../../movies/movie-details-modal/movie-details-modal.component";
import {User} from "../../users/shared/user";
import {LoginService} from "../../users/shared/login.service";

@Component({
  selector: 'app-sign-in',
  templateUrl: 'sign-in.component.html',
  styleUrls: ['sign-in.component.css']
})
export class SignInComponent implements OnInit {
  title: string;
  user: User;

  constructor(private loginService: LoginService) { }

  ngOnInit() {
    this.title = "Sign in";

    this.user = new User();
  }

  save(userModel: User, isValid: boolean) {
    console.log(userModel, isValid);

    if(isValid) {
      this.loginService.authenticate(userModel.username, userModel.password).then(user => {
        console.log("User on login: " + user);
      }).catch(this.handleError);
    }
  }

  isAuthenticated() {
    return this.loginService.isAuthenticated();
  }

  logout() {
    this.loginService.logout(true);
  }

  private handleError(error: any) {
    console.error('An error occurred', error); // for demo purposes only
    //return Promise.reject(error.message || error);
  }

  inputHasErrors(input) {
    return input.errors && (input.dirty || input.touched)
  }

}
