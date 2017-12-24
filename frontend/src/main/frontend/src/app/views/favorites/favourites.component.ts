import {Component, OnInit} from '@angular/core';
import {UserService} from "../../service/user.service";
import {User} from "../../shared/users/user";
import {FavouriteMedia} from "../../shared/favorites/FavouriteMedia";
import 'rxjs/add/operator/map'
import {Router} from "@angular/router";
import {Movie} from "../../shared/movie/movie";
import {LoginService} from "../../service/login.service";
import {UserFavouritesService} from "../../service/user-favourites.service";
import {Category} from "../../shared/favorites/category/category";

@Component({
    selector: 'app-favourites',
    templateUrl: './favourites.component.html',
    styleUrls: ['./favourites.component.css']
})
export class FavouritesComponent implements OnInit {
    user: User = new User();
    fav: FavouriteMedia[];
    favouriteMedia: Movie[];

    currentCategory: Category;

    constructor(private router: Router, private userFavouritesService: UserFavouritesService,
                private userService: UserService, private loginService: LoginService) {
    }

    ngOnInit() {
        //this.getLoggedUser();
        //this.getFavouriteMedia().subscribe(media => this.fav = media);

        this.getFavourites();

        let childCategory = new Category();
        childCategory.name = "Watch with friends";

        let rootCategory = new Category();
        rootCategory.name = "Home";
        rootCategory.subCategories = [childCategory, childCategory, childCategory, childCategory, childCategory, childCategory, childCategory];
        childCategory.parent = rootCategory;

        this.currentCategory = rootCategory;
    }

    getFavourites() {
        this.userFavouritesService.getFavourites()
            .then(media => this.favouriteMedia = media);
    }

    getLoggedUser() {
        this.userService.getUser(1).then((user: User) => {
            this.user = user;
            this.fav = this.user.favourites;
            console.log(this.fav.pop());
        });
    }

    onSelectMovie(movie: Movie) {
        this.router.navigate(['/movie', movie.id]);
    }

}
