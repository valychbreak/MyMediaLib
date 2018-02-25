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
import {CategoryService} from "../../service/category.service";

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
                private userService: UserService, private categoryService: CategoryService) {
    }

    ngOnInit() {
        //this.getLoggedUser();
        //this.getFavouriteMedia().subscribe(media => this.fav = media);

        this.getFavourites();

        this.loadRootCategory();

        /*let childCategory = new Category();
        childCategory.name = "Watch with friends";

        let rootCategory = new Category();
        rootCategory.name = "Home";
        rootCategory.subCatalogs = [childCategory, childCategory, childCategory, childCategory, childCategory, childCategory, childCategory];

        let parentWithoutSubCategories = Category.copyOf(rootCategory);
        parentWithoutSubCategories.subCatalogs = null;
        childCategory.parent = parentWithoutSubCategories;

        this.currentCategory = rootCategory;*/
    }

    private loadRootCategory() {
        this.categoryService.getRootCategory()
            .then(category => this.currentCategory = category);
    }

    getFavourites() {
        this.userFavouritesService.getFavourites()
            .then(media => this.favouriteMedia = media);
    }

    onSelectMovie(movie: Movie) {
        this.router.navigate(['/movie', movie.id]);
    }

}
