import {Component, Input, OnInit} from '@angular/core';
import {Category} from "../../../../shared/favorites/category/category";

@Component({
    selector: 'category-path',
    templateUrl: './category-path.component.html',
    styleUrls: ['./category-path.component.css']
})
export class CategoryPathComponent implements OnInit {

    @Input()
    private currentCategory: Category;

    constructor() {
    }

    ngOnInit() {
    }

    getAllParentCategories(): Category[] {
        return this.getParentCategoryRecursively(this.currentCategory);
    }

    getParentCategoryRecursively(category: Category): Category[] {
        let categoryList: Category[] = [];
        if (category.parent) {
            categoryList = this.getParentCategoryRecursively(category.parent);
        }
        categoryList.push(category);
        return categoryList;
    }

    isCurrentCategory(category: Category): boolean {
        return category == this.currentCategory;
    }

    isCategoryDefined(): boolean {
        return !!this.currentCategory;
    }
}
