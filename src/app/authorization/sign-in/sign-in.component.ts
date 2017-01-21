import { Component, OnInit } from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {MovieDetailsModalComponent} from "../../movies/movie-details-modal/movie-details-modal.component";
import {User} from "../../users/shared/user";

@Component({
  selector: 'app-sign-in',
  templateUrl: 'sign-in.component.html',
  styleUrls: ['sign-in.component.css']
})
export class SignInComponent implements OnInit {
  title: string;
  user: User;

  constructor(private modalService: NgbModal) { }

  ngOnInit() {
    this.title = "Sign in"

    this.user = new User();
  }

  save(userModel: User, isValid: boolean) {
    console.log(userModel, isValid);
  }

}
