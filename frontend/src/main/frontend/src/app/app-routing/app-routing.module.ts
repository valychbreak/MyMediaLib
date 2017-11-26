import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {SignInComponent} from "../views/auth/sign-in/sign-in.component";
import {UserFormComponent} from "../views/users/user-form/user-form.component";
import {UsersComponent} from "../views/users/users.component";
import {FavouritesComponent} from "../views/favorites/favourites.component";
import {MovieViewComponent} from "../movies/movie-details/movie-details.component";
import {MoviesComponent} from "../movies/movies.component";
import {SignUpComponent} from "../views/auth/sign-up/sign-up.component";

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
  },
  {
    path: 'signup',
    component: SignUpComponent
  },
  {
    path: '',
    component: SignInComponent
  }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
