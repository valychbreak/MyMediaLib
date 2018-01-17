export class Category {
    id: number;
    name: string;
    parent: Category;
    subCategories: Category[];

    static copyOf(category: Category): Category {
        let copiedCategory = new Category();

        copiedCategory.id = category.id;
        copiedCategory.name = category.name;
        copiedCategory.parent = category.parent;
        copiedCategory.subCategories = category.subCategories;

        return copiedCategory;
    }
}