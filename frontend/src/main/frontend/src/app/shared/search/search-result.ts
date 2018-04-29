export class SearchResult<T> {
    page: number;
    totalPages: number;
    totalResults: number;
    items: T[];
}