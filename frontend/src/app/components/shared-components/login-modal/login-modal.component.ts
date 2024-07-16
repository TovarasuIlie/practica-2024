import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';

@Component({
  selector: 'app-login-modal',
  templateUrl: './login-modal.component.html',
  styleUrl: './login-modal.component.css'
})
export class LoginModalComponent implements OnInit {
  loginForm: FormGroup = new FormGroup({});
  matcher = new ErrorStateMatcher();

  constructor(private fb: FormBuilder) {

  }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.loginForm = this.fb.group({
      email:    [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required, Validators.minLength(8)]]
    })
  }
  
  login() {
    if(this.loginForm.valid) {
      console.log(this.loginForm.getRawValue());
    }
  }

  resetForm() {
    this.loginForm.reset();
  }
}
