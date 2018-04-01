
import {Injectable} from "@angular/core";
import {MediaCollection} from "../shared/favorites/collection/media-collection";
import {Config} from "../config/config";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class MediaCollectionService {
    static GET_ROOT_CATEGORY_LINK = Config.dataRequestLink + "/user/collection/root";
    static GET_ALL_COLLECTIONS_LINK = Config.dataRequestLink + "/user/collection/all";
    static GET_CATEGORY_LINK = Config.dataRequestLink + "/collection/";
    static ADD_CATEGORY_LINK = Config.dataRequestLink + "/collection/add";

    constructor(private http: HttpClient) {
    }

    getRootCategory(): Promise<MediaCollection> {
        return this.http.get<MediaCollection>(MediaCollectionService.GET_ROOT_CATEGORY_LINK)
            .toPromise()
            .then(category => category);
    }

    getAllCollections(): Promise<MediaCollection[]> {
        return this.http.get<MediaCollection[]>(MediaCollectionService.GET_ALL_COLLECTIONS_LINK )
            .toPromise()
            .then(collections => collections);
    }

    getCategory(id: number): Promise<MediaCollection> {
        return this.http.get<MediaCollection>(MediaCollectionService.GET_CATEGORY_LINK + id + "?media=true")
            .toPromise()
            .then(category => category);
    }

    addNewCategory(newCategory: MediaCollection): Promise<MediaCollection> {
        return this.http.post<MediaCollection>(MediaCollectionService.ADD_CATEGORY_LINK, newCategory)
            .toPromise()
            .then(category => category);
    }
}