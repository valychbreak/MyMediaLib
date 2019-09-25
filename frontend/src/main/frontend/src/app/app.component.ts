import {Component, OnInit} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {LoginService} from "./service/login.service";
import {AccountEventsService} from "./account/account-events.service";
import {UserAppSetings} from "./config/user-app-settings";
import {User} from "./shared/users/user";
import {RelativeNavigationLink} from "./config/relative-navigation-link";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    user: User;

    constructor(private router: Router, private loginService: LoginService, private accountEventsService: AccountEventsService) {
        accountEventsService.subscribe((account) => {
            if (!account.authenticated) {
                this.user = null;
            } else {
                this.updateUser();
            }
        });

        router.events.subscribe((event) => {
            if (event instanceof NavigationEnd) {
                console.log(event.url);
            }
        });
        /*router.events.subscribe(e => {
          //console.log(e.url);
          /!*if(!this.isAuthenticated() && (e.url != '/signin' && e.url != '/signup')) {
            this.loginService.checkAuthOnServer().subscribe(data => {
              let result: boolean;
              result = data == "true";
              if (!result) {
                router.navigate(['/signin']);
              }
            });
          }*!/
          if(!this.isAuthenticated() && (e.url != '/signin' && e.url != '/signup')) {
            router.navigate(['/signin']);
          }
        });*/
    }

    private updateUser() {
        this.user = this.accountEventsService.getUser();
    }

    ngOnInit(): void {
        this.updateUser();
    }

    isAuthenticated(): boolean {
        return this.user != null;
    }

    logout() {
        this.loginService.logout(true);
        this.user = null;

        this.router.navigateByUrl(RelativeNavigationLink.SIGN_IN);
    }

    getSidebarToggle(): boolean {
        return UserAppSetings.sidebarToggle;
    }

    toggleSidebar() {
        UserAppSetings.sidebarToggle = !UserAppSetings.sidebarToggle;
    }
}
