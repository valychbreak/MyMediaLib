import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

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
import {MovieService} from "./movies/shared/movie.service";

@NgModule({
  declarations: [
    AppComponent,
    MoviesComponent,
    SignInComponent,
    FavouritesComponent,
    MovieViewComponent,
    MovieDetailsModalComponent
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
  providers: [MovieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
