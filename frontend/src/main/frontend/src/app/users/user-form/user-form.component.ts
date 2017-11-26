import { Component, OnInit } from '@angular/core';
import {User} from "../../shared/users/user";
import {ActivatedRoute, Router, Params} from "@angular/router";
import {UserService} from "../../shared/users/user.service";

import 'rxjs/add/operator/switchMap';

@Component({
  //moduleId: module.id,
  selector: 'app-user-form',
  templateUrl: 'user-form.component.html',
  styleUrls: ['user-form.component.css']
})
export class UserFormComponent implements OnInit {
  model = new User();

  submitted = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: UserService
  ) {}

  ngOnInit() {
    let id = +this.route.snapshot.params['id'];
    if(!isNaN(id)) {
      console.log(id);
      this.service.getUser(id)
        .then((user: User) => this.model = user);
    }

  }

  onSubmit() { this.submitted = true; }

  get diagnostic() {
    return JSON.stringify(this.model);
  }

}
