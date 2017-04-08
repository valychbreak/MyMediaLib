import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "./users/shared/login.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app works!';

  constructor(router: Router, private loginService: LoginService) {
    router.events.subscribe(e => {
      console.log(e.url);
      if(!this.isAuthenticated() && (e.url != '/signin' && e.url != '/signup')) {
        router.navigate(['/signin']);
      }
    })
  }

  isAuthenticated() {
    return this.loginService.isAuthenticated();
  }
}
