
import {Injectable} from "@angular/core";
import {MediaCollection} from "../shared/favorites/collection/media-collection";
import {Config} from "../config/config";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class MediaCollectionService {
    static GET_ROOT_CATEGORY_LINK = Config.dataRequestLink + "/user/catalog/root";
    static GET_CATEGORY_LINK = Config.dataRequestLink + "/user/catalog/";
    static ADD_CATEGORY_LINK = Config.dataRequestLink + "/catalog/add";

    constructor(private http: HttpClient) {
    }

    getRootCategory(): Promise<MediaCollection> {
        return this.http.get<MediaCollection>(MediaCollectionService.GET_ROOT_CATEGORY_LINK)
            .toPromise()
            .then(category => category);
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