import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {AppComponent} from './app.component';
import {UsersModule} from "./views/users/users.module";
import {MoviesComponent} from "./views/movies/movies.component";
import {MovieViewComponent} from "./views/movies/movie-details/movie-details.component";
import {FavouritesComponent} from "./views/favorites/favourites.component";
import {SignInComponent} from './views/auth/sign-in/sign-in.component';
import {AppRoutingModule} from "./app-routing/app-routing.module";
import {MovieDetailsModalComponent} from './views/movies/movie-details-modal/movie-details-modal.component';
import {MovieService} from "./service/movie.service";
import {SignUpComponent} from './views/auth/sign-up/sign-up.component';
import {LoginService} from "./service/login.service";
import {MovieShortViewComponent} from './views/movies/movie-short-view/movie-short-view.component';
import {UserFavouritesService} from "./service/user-favourites.service";
import {AccountEventsService} from "./account/account-events.service";
import {PersonShortViewComponent} from './views/people/person-short-view/person-short-view.component';
import {PeopleService} from "./service/people.service";
import {Ng4LoadingSpinnerModule} from "ng4-loading-spinner";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthenticationInterceptor} from "./interceptor/authentication-interceptor";
import { MovieSearchSectionComponent } from './views/movies/movie-search-section/movie-search-section.component';


@NgModule({
    declarations: [
        AppComponent,
        MoviesComponent,
        SignInComponent,
        FavouritesComponent,
        MovieViewComponent,
        MovieDetailsModalComponent,
        SignUpComponent,
        MovieShortViewComponent,
        PersonShortViewComponent,
        MovieSearchSectionComponent
    ],
    entryComponents: [
        MovieDetailsModalComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        AppRoutingModule,
        NgbModule.forRoot(),
        UsersModule,
        Ng4LoadingSpinnerModule.forRoot()
    ],
    providers: [PeopleService, MovieService, LoginService, UserFavouritesService, AccountEventsService, {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthenticationInterceptor,
            multi: true,
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
