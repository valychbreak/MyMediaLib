import { Component, OnInit } from '@angular/core';
import {AbstractForm} from "../../base/form";
import {User} from "../../users/shared/user";
import {UserService} from "../../users/shared/user.service";

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
      userModel.lastName = userModel.firstName;
      this.userService.addUser(userModel)
          .then(user => {
            console.log("User was created successfully. " + user);
          })
    }
  }
}
