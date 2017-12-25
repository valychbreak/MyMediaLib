
import {Injectable} from "@angular/core";
import {Category} from "../shared/favorites/category/category";
import {Config} from "../config/config";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class CategoryService {
    static GET_ROOT_CATEGORY_LINK = Config.dataRequestLink + "/user/catalog/root";
    static GET_CATEGORY_LINK = Config.dataRequestLink + "/user/catalog/";

    constructor(private http: HttpClient) {
    }

    getRootCategory(): Promise<Category> {
        return this.http.get<Category>(CategoryService.GET_ROOT_CATEGORY_LINK)
            .toPromise()
            .then(category => category);
    }

    getCategory(id: number): Promise<Category> {
        return this.http.get<Category>(CategoryService.GET_CATEGORY_LINK + id + "?media=true")
            .toPromise()
            .then(category => category);
    }
}