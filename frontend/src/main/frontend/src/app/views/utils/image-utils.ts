
export class ImageUtils {
    public static getNoImageIfEmptyOrNull(imagePath: string): string {
        if (!imagePath) {
            return "http://via.placeholder.com/256x384?text=No+Image"
        } else {
            return imagePath;
        }
    }
}