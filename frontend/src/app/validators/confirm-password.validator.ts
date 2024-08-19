import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export class CustomValidators {

}

export const confirmPasswordValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    if (control.get('confirmPassword')?.value !== null) {
        if(control.get('password')?.value == control.get('confirmPassword')?.value) {
            control.get('confirmPassword')?.setErrors(null);
            return null;
        } else {
            control.get('confirmPassword')?.setErrors({ 'unmatched': true });
            return { unmatched: true };
        }
    } else {
        return null;
    }
}