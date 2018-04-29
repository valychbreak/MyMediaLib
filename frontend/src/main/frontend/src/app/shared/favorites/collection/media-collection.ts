import {Movie} from "../../movie/movie";

export class MediaCollection {
    id: number;
    name: string;
    parent: MediaCollection;
    subCollections: MediaCollection[];
    mediaList: Movie[];

    static copyOf(category: MediaCollection): MediaCollection {
        let copiedCategory = new MediaCollection();

        copiedCategory.id = category.id;
        copiedCategory.name = category.name;
        copiedCategory.parent = category.parent;
        copiedCategory.subCollections = category.subCollections;
        copiedCategory.mediaList = category.mediaList;

        return copiedCategory;
    }
}