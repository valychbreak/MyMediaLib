import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

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
import { PeopleSearchSectionComponent } from './views/people/people-search-section/people-search-section.component';
import {BusyModule} from "angular2-busy";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { CategoryPathComponent } from './views/favorites/collection/collection-path/collection-path.component';
import { SubCategoriesListViewComponent } from './views/favorites/collection/sub-collections-list-view/sub-collections-list-view.component';
import {MediaCollectionService} from "./service/media-collection.service";
import { NewCategoryViewComponent } from './views/favorites/collection/new-collection-view/new-collection-view.component';
import { MediaSearchSectionComponent } from './views/movies/media-search-section/media-search-section.component';
import { TvShowSearchSectionComponent } from './views/movies/tvshow-search-section/tvshow-search-section.component';
import {
    MdcButtonModule,
    MdcCardModule, MdcDialogModule,
    MdcDrawerModule,
    MdcFabModule, MdcFormFieldModule,
    MdcIconModule, MdcIconToggleModule,
    MdcListModule, MdcMenuModule, MdcRippleModule, MdcTextFieldModule,
    MdcToolbarModule
} from '@angular-mdc/web';
import { CollectionViewComponent } from './views/favorites/collection/collection-view/collection-view.component';
import {NewCollectionDialogComponent} from "./views/favorites/collection/new-collection-view/new-collection-dialog.component";
import { RemoveCollectionDialogComponent } from './views/favorites/collection/remove-collection-dialog/remove-collection-dialog.component';


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
        MovieSearchSectionComponent,
        PeopleSearchSectionComponent,
        CategoryPathComponent,
        SubCategoriesListViewComponent,
        NewCategoryViewComponent,
        MediaSearchSectionComponent,
        TvShowSearchSectionComponent,
        CollectionViewComponent,
        NewCollectionDialogComponent,
        RemoveCollectionDialogComponent
    ],
    entryComponents: [
        MovieDetailsModalComponent,
        NewCategoryViewComponent,
        NewCollectionDialogComponent,
        RemoveCollectionDialogComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        AppRoutingModule,
        NgbModule.forRoot(),
        UsersModule,
        Ng4LoadingSpinnerModule.forRoot(),
        BrowserAnimationsModule,
        BusyModule,
        MdcButtonModule,
        MdcFabModule,
        MdcIconModule,
        MdcToolbarModule,
        MdcDrawerModule,
        MdcListModule,
        MdcCardModule,
        MdcIconToggleModule,
        MdcRippleModule,
        MdcFabModule,
        MdcDialogModule,
        MdcFormFieldModule,
        MdcTextFieldModule,
        MdcMenuModule,
        BrowserModule
    ],
    providers: [PeopleService, MovieService, LoginService, UserFavouritesService, AccountEventsService, MediaCollectionService, {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthenticationInterceptor,
            multi: true,
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
