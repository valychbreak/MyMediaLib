import {BasicMovie} from "../movie/basic-movie";

export class Person {
    name: string;
    isAdult: boolean;
    image: string;
    popularity: number;
    knownFor: BasicMovie[];
}