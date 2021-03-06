import {Component, OnInit} from '@angular/core';
import {UserService} from "../../service/user.service";
import {User} from "../../shared/users/user";
import {Router} from "@angular/router";

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
    users: User[];

    constructor(private router: Router, private userService: UserService) {

    }

    ngOnInit() {
        this.getUsers();
    }

    getUsers(): void {
        this.userService.getUsers().then(users => this.users = users);
    }

    onSelectUser(user: User) {
        this.router.navigate(['/user', user.id]);
    }
}
