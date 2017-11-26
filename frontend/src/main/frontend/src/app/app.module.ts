import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {Http, HttpModule, RequestOptions, XHRBackend} from '@angular/http';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import {UsersComponent} from "./users/users.component";
import {UsersModule} from "./users/users.module";
import { UserFormComponent } from './users/user-form/user-form.component';
import {MoviesComponent} from "./movies/movies.component";
import {MovieViewComponent} from "./movies/movie-details/movie-details.component";
import {FavouritesComponent} from "./favourites/favourites.component";
import { SignInComponent } from './authorization/sign-in/sign-in.component';
import {AppRoutingModule} from "./app-routing/app-routing.module";
import { MovieDetailsModalComponent } from './movies/movie-details-modal/movie-details-modal.component';
import {MovieService} from "./shared/movie/movie.service";
import { SignUpComponent } from './authorization/sign-up/sign-up.component';
import {LoginService} from "./shared/users/login.service";
import { MovieShortViewComponent } from './movies/movie-short-view/movie-short-view.component';
import {UserFavouritesService} from "./shared/users/user-favourites.service";
import {CustomHttpService} from "./utils/custom-http-service";
import {AccountEventsService} from "./account/account-events.service";
import { PersonShortViewComponent } from './person/person-short-view/person-short-view.component';

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
  providers: [MovieService, LoginService, UserFavouritesService, AccountEventsService, {
    provide: Http,
    useFactory: httpFactory,
    deps: [XHRBackend, RequestOptions, AccountEventsService],
    multi: false
  }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
