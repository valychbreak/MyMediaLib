import {Injectable} from "@angular/core";
import {MediaCollection} from "../shared/favorites/collection/media-collection";
import {Config} from "../config/config";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Movie} from "../shared/movie/movie";

@Injectable()
export class MediaCollectionService {
    static GET_ALL_COLLECTIONS_LINK = Config.DATA_REQUEST_LINK + "/user/collection/all";
    static GET_CATEGORY_LINK = Config.DATA_REQUEST_LINK + "/collection/";
    static ADD_CATEGORY_LINK = Config.DATA_REQUEST_LINK + "/collection/add";

    constructor(private http: HttpClient) {
    }

    getAllCollections(): Promise<MediaCollection[]> {
        return this.http.get<MediaCollection[]>(MediaCollectionService.GET_ALL_COLLECTIONS_LINK)
            .toPromise()
            .then(collections => collections);
    }

    getCategory(id: number): Promise<MediaCollection> {

        let params = new HttpParams();
        params = params.append('media', 'true');

        return this.http.get<MediaCollection>(
            MediaCollectionService.GET_CATEGORY_LINK + id, { params: params })
            .toPromise()
            .then(category => category);
    }

    addNewCategory(newCategory: MediaCollection): Promise<MediaCollection> {
        return this.http.post<MediaCollection>(MediaCollectionService.ADD_CATEGORY_LINK, newCategory)
            .toPromise()
            .then(category => category);
    }

    addMediaToCategory(media: Movie, collection: MediaCollection) {
        return this.http.post(Config.DATA_REQUEST_LINK + "/collection/" + collection.id + "/add-media", {
            imdbId: media.imdbId,
            title: media.title
        })
            .toPromise()
            .then(response => response);
    }

    removeCollection(collection: MediaCollection): Promise<any> {
        return this.http.post(Config.DATA_REQUEST_LINK + "/collection/" + collection.id + "/remove", null)
            .toPromise()
            .then(response => response);
    }
}