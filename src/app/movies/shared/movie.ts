export class Movie {
  id: number;
  imagePath: string;
  name: string;
  genre: string;
  duration: number;
  shortDescription: string;
  description: string;
  stars: number;
  reviews: number;


  constructor() {
  }

/*constructor(id: number, imagePath: string, name: string, duration: number, shortDescription: string, description: string, stars: number, reviews: number) {
    this.id = id;
    this.imagePath = imagePath;
    this.name = name;
    this.duration = duration;
    this.shortDescription = shortDescription;
    this.description = description;
    this.stars = stars;
    this.reviews = reviews;
  }*/
}
