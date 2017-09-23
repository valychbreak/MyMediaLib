export class Movie {
  id: number;
  imagePath: string;
  title: string;
  genre: string;
  duration: number;
  shortDescription: string;
  description: string;
  stars: number;
  reviews: number;
  imdbId: string;
  isFavourite: boolean;
  type: string;


  constructor() {
  }

/*constructor(id: number, imagePath: string, title: string, duration: number, shortDescription: string, description: string, stars: number, reviews: number) {
    this.id = id;
    this.imagePath = imagePath;
    this.title = title;
    this.duration = duration;
    this.shortDescription = shortDescription;
    this.description = description;
    this.stars = stars;
    this.reviews = reviews;
  }*/
}
