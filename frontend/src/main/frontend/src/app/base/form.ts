export abstract class AbstractForm {
    inputHasErrors(input) {
        return false;//input.errors && (input.dirty || input.touched)
    }
}