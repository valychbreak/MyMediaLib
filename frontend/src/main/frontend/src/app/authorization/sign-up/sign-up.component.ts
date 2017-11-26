import { Component, OnInit } from '@angular/core';
import {AbstractForm} from "../../base/form";
import {User} from "../../shared/users/user";
import {UserService} from "../../shared/users/user.service";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent extends AbstractForm implements OnInit {

  user: User;

  constructor(private userService: UserService) {
    super();
  }

  ngOnInit() {
    this.user = new User();
  }

  save(userModel: User, isValid: boolean) {
    console.log(userModel, isValid);
    if(isValid) {
      this.userService.addUser(userModel)
          .then(user => {
            console.log("User was created successfully. " + user);
          })
    }
  }
}
