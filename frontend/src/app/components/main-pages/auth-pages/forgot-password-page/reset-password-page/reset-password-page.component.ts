import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AccountService } from '../../../../../services/account.service';
import { Router } from '@angular/router';
import { ToastComponent } from '../../../../shared-components/toast/toast.component';
import { ToastService } from '../../../../../services/toast.service';

@Component({
  selector: 'app-reset-password-page',
  templateUrl: './reset-password-page.component.html',
  styleUrl: './reset-password-page.component.css'
})
export class ResetPasswordPageComponent {
  verifyCodeForm: FormGroup = new FormGroup({});
  matcher: ErrorStateMatcher = new ErrorStateMatcher();
  errorMessages: string[] = [];

  constructor(private fb: FormBuilder, private accountService: AccountService, private router: Router, private toastService: ToastService) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.verifyCodeForm = this.fb.group({
      password:         [null, [Validators.required, Validators.minLength(8), Validators.pattern(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/)]],
      confirmPassword:  [null, [Validators.required, Validators.minLength(8)]],
      code: [null, [Validators.required, Validators.minLength(6), Validators.maxLength(6)]]
    })
  }

  resetPassword() {
    this.errorMessages = [];
    if(this.verifyCodeForm.valid) {
      this.accountService.resetPassword(this.verifyCodeForm.get("code")?.value, this.verifyCodeForm.get("password")?.value).subscribe({
        next: (response: any) => {
          this.toastService.show({title: "Resetare Parola", message: response.message, classname: "text-success"});
          this.router.navigateByUrl('/');
        },
        error: (response) => {
          console.log(response)
          this.errorMessages.push(response.error.message);
        }
      })
    }
  }
}
