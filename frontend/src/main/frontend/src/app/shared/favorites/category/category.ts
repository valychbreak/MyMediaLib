export class Category {
    id: number;
    name: string;
    parent: Category;
    subCategories: Category[];
}