export abstract class AbstractForm {
    inputHasErrors(input) {
        return input.errors && (input.dirty || input.touched)
    }
}