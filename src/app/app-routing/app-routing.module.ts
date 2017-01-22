import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {SignInComponent} from "../authorization/sign-in/sign-in.component";
import {UserFormComponent} from "../users/user-form/user-form.component";
import {UsersComponent} from "../users/users.component";
import {FavouritesComponent} from "../favourites/favourites.component";
import {MovieViewComponent} from "../movies/movie-details/movie-details.component";
import {MoviesComponent} from "../movies/movies.component";

const routes: Routes = [
  {
    path: 'movies',
    component: MoviesComponent,
    data: {
      title: 'Users List'
    }
  },
  {
    path: 'movie/:id',
    component: MovieViewComponent,
    data: {
      title: "Movie"
    }
  },
  {
    path: 'favourites',
    component: FavouritesComponent,
  },
  {
    path: 'users',
    component: UsersComponent,
    data: {
      title: 'Users List'
    }
  },
  {
    path: 'user/add',
    component: UserFormComponent,
  },
  {
    path: 'user/:id',
    component: UserFormComponent,
  },
  {
    path: 'signin',
    component: SignInComponent
  }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
