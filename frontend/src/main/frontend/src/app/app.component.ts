import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "./users/shared/login.service";
import {Observable} from "rxjs";
import {AccountEventsService} from "./account/account-events.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app works!';

  constructor(router: Router, private loginService: LoginService, private accountEventsService: AccountEventsService) {
    accountEventsService.subscribe((account) => {
      if(!account.authenticated) {
        this.loginService.logout(false);
      }
    });

    router.events.subscribe(e => {
      //console.log(e.url);
      /*if(!this.isAuthenticated() && (e.url != '/signin' && e.url != '/signup')) {
        this.loginService.checkAuthOnServer().subscribe(data => {
          let result: boolean;
          result = data == "true";
          if (!result) {
            router.navigate(['/signin']);
          }
        });
      }*/
      if(!this.isAuthenticated() && (e.url != '/signin' && e.url != '/signup')) {
        router.navigate(['/signin']);
      }
    });
  }

  isAuthenticated(): boolean {
    return this.loginService.isAuthenticated();
  }
}