import {Component, Input, OnInit} from '@angular/core';
import {MediaCollection} from "../../../../shared/favorites/collection/media-collection";

@Component({
    selector: 'category-path',
    templateUrl: './collection-path.component.html',
    styleUrls: ['./collection-path.component.css']
})
export class CategoryPathComponent implements OnInit {

    @Input()
    private currentCategory: MediaCollection;

    constructor() {
    }

    ngOnInit() {
    }

    getAllParentCategories(): MediaCollection[] {
        return this.getParentCategoryRecursively(this.currentCategory);
    }

    getParentCategoryRecursively(category: MediaCollection): MediaCollection[] {
        let categoryList: MediaCollection[] = [];
        if (category.parent) {
            categoryList = this.getParentCategoryRecursively(category.parent);
        }
        categoryList.push(category);
        return categoryList;
    }

    isCurrentCategory(category: MediaCollection): boolean {
        return category == this.currentCategory;
    }

    isCategoryDefined(): boolean {
        return !!this.currentCategory;
    }
}
