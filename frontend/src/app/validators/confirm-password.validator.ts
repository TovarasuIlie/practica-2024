import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export class CustomValidators {

}

export const confirmPasswordValidator: ValidatorFn = (control: AbstractControl): ValidationErrors|null => {
    if(control.get('password')) {
        if(control.get('confirmPassword')?.value !== null) {
            return (control.get('password')?.value == control.get('confirmPassword')?.value) ? null : { unmatched: true };
        } else {
            return null;
        }
    } else {
        return (control.get('resetPassword')?.value == control.get('resetPasswordConfirm')?.value) ? null : { unmatched: true };
    }
}