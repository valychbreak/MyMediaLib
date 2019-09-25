import { Component, OnInit } from '@angular/core';
import {AccountEventsService} from "../../../account/account-events.service";
import {User} from "../../../shared/users/user";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user: User;

  constructor(private accountEventsService: AccountEventsService) { }

  ngOnInit() {
    this.user = this.accountEventsService.getUser();
  }

}
