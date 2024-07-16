import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';

@Component({
  selector: 'app-forgot-password-page',
  templateUrl: './forgot-password-page.component.html',
  styleUrl: './forgot-password-page.component.css'
})
export class ForgotPasswordPageComponent implements OnInit {
  forgotForm: FormGroup = new FormGroup({});

  matcher: ErrorStateMatcher = new ErrorStateMatcher();

  constructor(private fb: FormBuilder) {

  }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.forgotForm = this.fb.group({
      email:            [null, [Validators.required, Validators.email]],
    });
  }

  sendEmail() {
    if(this.forgotForm.valid) {
      console.log(this.forgotForm.getRawValue());
    }
  }
}
