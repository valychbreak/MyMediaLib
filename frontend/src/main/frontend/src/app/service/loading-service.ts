import {Injectable} from "@angular/core";
import {Ng4LoadingSpinnerService} from "ng4-loading-spinner";

@Injectable()
export class LoadingService {
    constructor(private ng4LoadingSpinnerService: Ng4LoadingSpinnerService) {

    }

    show() {
        this.ng4LoadingSpinnerService.show();
    }

    hide() {
        this.ng4LoadingSpinnerService.hide();
    }
}