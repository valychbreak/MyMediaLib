import {Component, OnInit, Injector} from '@angular/core';
import {Movie} from "../../../shared/movie/movie";
import {MovieService} from "../../../service/movie.service";
import {ActivatedRoute} from "@angular/router";
import {AbstractMovieDetails} from "../../../shared/movie/base-movie-details";
import {MediaCollection} from "../../../shared/favorites/collection/media-collection";
import {MediaCollectionService} from "../../../service/media-collection.service";

@Component({
    selector: 'app-movie-view',
    templateUrl: 'movie-details.component.html',
    styleUrls: ['movie-details.component.scss']
})
export class MovieViewComponent extends AbstractMovieDetails implements OnInit {
    movie = new Movie();
    mediaCollections: MediaCollection[];

    constructor(private movieService: MovieService, private route: ActivatedRoute, private injector: Injector,
                private mediaCollectionService: MediaCollectionService) {
        super(injector);
    }

    ngOnInit() {
        /*let id = +this.route.snapshot.params['id'];
        if(!isNaN(id)) {
          console.log(id);
          this.movieService.getMovie(id)
            .then((movie: Movie) => this.movie = movie);
        }*/
        let id = this.route.snapshot.params['id'];
        this.movieService.getMovieByImdbId(id)
            .then((movie: Movie) => this.movie = movie);

        this.mediaCollectionService.getAllCollections()
            .then(collections => this.mediaCollections = collections)
    }

    handleMenuSelect(event: { index: number, item: HTMLElement }) {
        let mediaCollection = this.mediaCollections[event.index];

        this.mediaCollectionService.addMediaToCategory(this.movie, mediaCollection)
            .then(response => {
                console.log("Media " + this.movie.title + " was successfully added to " + mediaCollection.name + " collection");
            })
    }

}
