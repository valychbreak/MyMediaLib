import {Component, OnInit} from '@angular/core';
import {NavigationEnd, NavigationStart, Router} from "@angular/router";
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

        router.events.subscribe(e => {
            if (e instanceof NavigationStart) {
                if (!this.isAuthenticated() && (e.url != '/signin' && e.url != '/signup')) {
                    console.log("User is not authenticated. Redirecting to /signin");
                    router.navigate(['/signin']);
                }
            }
        });

        router.events.subscribe(e => {
            if (e instanceof NavigationEnd) {
                console.log("Successfully navigated to " + e.url);
            }
        });
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
