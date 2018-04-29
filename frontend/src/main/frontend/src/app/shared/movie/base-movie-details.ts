import {Movie} from "./movie";
import {UserFavouritesService} from "../../service/user-favourites.service";
import {Injector} from "@angular/core";

export abstract class AbstractMovieDetails {
    protected userFavouritesService: UserFavouritesService

    constructor(injector: Injector) {
        this.userFavouritesService = injector.get(UserFavouritesService);
    }

    addToFavourites(movie: Movie) {
        movie.isFavourite = true;
        this.userFavouritesService.addMedia(movie);
    }

    removeFromFavourites(movie: Movie) {
        movie.isFavourite = false;
        this.userFavouritesService.removeMedia(movie);
    }
}