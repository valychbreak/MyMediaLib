import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MediaCollectionService} from "../../../../service/media-collection.service";
import {MediaCollection} from "../../../../shared/favorites/collection/media-collection";

@Component({
    selector: 'app-collection-view',
    templateUrl: './collection-view.component.html',
    styleUrls: ['./collection-view.component.scss']
})
export class CollectionViewComponent implements OnInit {

    collection = new MediaCollection();

    constructor(private mediaCollectionService: MediaCollectionService, private route: ActivatedRoute) {
    }

    ngOnInit() {
        let id = this.route.snapshot.params['id'];
        this.mediaCollectionService.getCategory(id)
            .then((collection: MediaCollection) => this.collection = collection);
    }

}
