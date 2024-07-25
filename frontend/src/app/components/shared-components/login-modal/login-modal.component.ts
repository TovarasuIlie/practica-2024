import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AuthService } from '../../../services/auth.service';
import { TokenService } from '../../../services/token.service';

@Component({
  selector: 'app-login-modal',
  templateUrl: './login-modal.component.html',
  styleUrl: './login-modal.component.css'
})
export class LoginModalComponent implements OnInit {
  loginForm: FormGroup = new FormGroup({});
  matcher = new ErrorStateMatcher();

  constructor(private fb: FormBuilder, private authService: AuthService, private tokenService: TokenService) {

  }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.loginForm = this.fb.group({
      username:    [null, [Validators.required]],
      password: [null, [Validators.required, Validators.minLength(1)]]
    })
  }
  
  login() {
    if(this.loginForm.valid) {
      this.authService.loginUser(this.loginForm.getRawValue()).subscribe({
        next: (response: any) => {
          console.log(response)
          this.tokenService.setToken = response.token as string;
        },
        error: (response) => {
          console.log(response)
          console.log("asda")
        }
      })
    }
  }

  resetForm() {
    this.loginForm.reset();
  }
}
