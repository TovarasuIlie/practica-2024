import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AccountService } from '../../../../../services/account.service';
import { Router } from '@angular/router';
import { ToastService } from '../../../../../services/toast.service';

@Component({
  selector: 'app-forgot-password-page',
  templateUrl: './forgot-password-page.component.html',
  styleUrl: './forgot-password-page.component.css'
})
export class ForgotPasswordPageComponent implements OnInit {
  forgotForm: FormGroup = new FormGroup({});
  matcher: ErrorStateMatcher = new ErrorStateMatcher();
  errorMessage: string[] = [];

  constructor(private fb: FormBuilder, private accountService: AccountService, private router: Router, private toastService: ToastService) {

  }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.forgotForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
    });
  }

  sendEmail() {
    this.errorMessage = [];
    if(this.forgotForm.valid) {
      this.accountService.sendCode(this.forgotForm.get('email')?.value).subscribe({
        next: (response: any) => {
          this.toastService.show({title: "Resetare Parola", message: response.message, classname: "text-success"});
          this.router.navigateByUrl("/reseteaza-parola/creaza-parola-noua");
        },
        error: (response) => {
          console.log(response);
          this.errorMessage.push(response.error.message)
        }
      })
    }
  }
}
