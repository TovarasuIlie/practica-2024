import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AuthService } from '../../../../services/auth.service';
import { response } from 'express';
import { Router } from '@angular/router';
import { UserRegister } from '../../../../models/user';
import { confirmPasswordValidator, CustomValidators } from '../../../../validators/confirm-password.validator';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.css'
})
export class RegisterPageComponent {
  registerForm: FormGroup = new FormGroup({});
  counties: string[] = ['Alba', 'Arad', 'Arges', 'Bacau', 'Bihor', 'Bistrita-Nasaud'];
  matcher = new ErrorStateMatcher();
  errorMessages: string[] = [];

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {

  }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.registerForm = this.fb.group({
      firstName:        [null, [Validators.required]],
      lastName:         [null, [Validators.required]],
      username:         [null, [Validators.required, Validators.pattern(/^(?![_.])(?!.*[_.]{2})[a-z0-9._-]+(?<![_.])$/), Validators.minLength(8), Validators.maxLength(20)]],
      email:            [null, [Validators.required, Validators.email, Validators.pattern(/\.[A-Za-z0-9]{2,4}$/i)]],
      password:         [null, [Validators.required, Validators.minLength(8), Validators.pattern(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/)]],
      confirmPassword:  [null, [Validators.required, Validators.minLength(8)]],
      county:           [null, [Validators.required]]
    }, 
    {
      validators: confirmPasswordValidator
    })
  }
  
  register() {
    this.errorMessages = [];
    if(this.registerForm.valid) {
      const newUser: UserRegister = {
        username: this.registerForm.get('username')?.value,
        firstName: this.registerForm.get('firstName')?.value,
        lastName: this.registerForm.get('lastName')?.value,
        email: this.registerForm.get('email')?.value,
        password: this.registerForm.get('password')?.value
      }
      this.authService.registerUser(newUser).subscribe({
        next: (response: any) => {
          this.router.navigateByUrl('/');
        },
        error: (response) => {
          console.log(response)
          this.errorMessages.push(response.error.message)
        }
      })
    } else {
      if(this.registerForm.errors?.['unmatched']) {
        this.registerForm.controls['confirmPassword'].setErrors({'unmatched': true});
      }
    }
  }
}
