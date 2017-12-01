import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Http, HttpModule, RequestOptions, XHRBackend} from '@angular/http';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {AppComponent} from './app.component';
import {UsersComponent} from "./views/users/users.component";
import {UsersModule} from "./views/users/users.module";
import {UserFormComponent} from './views/users/user-form/user-form.component';
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
import {CustomHttpService} from "./utils/custom-http-service";
import {AccountEventsService} from "./account/account-events.service";
import {PersonShortViewComponent} from './views/people/person-short-view/person-short-view.component';
import {PeopleService} from "./service/people.service";

export function httpFactory(backend: XHRBackend, options: RequestOptions, accountEventsService: AccountEventsService) {
    return new CustomHttpService(backend, options, accountEventsService);
}

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
        PersonShortViewComponent
    ],
    entryComponents: [
        MovieDetailsModalComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        AppRoutingModule,
        NgbModule.forRoot(),
        UsersModule
    ],
    providers: [PeopleService, MovieService, LoginService, UserFavouritesService, AccountEventsService, {
        provide: Http,
        useFactory: httpFactory,
        deps: [XHRBackend, RequestOptions, AccountEventsService],
        multi: false
    }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
