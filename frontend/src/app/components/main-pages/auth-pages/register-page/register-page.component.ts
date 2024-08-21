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
  countries: string[] = ["Alba", "Arad", "Argeș", "Bacău", "Bihor", "Bistrița-Năsăud", "Botoșani", "Brașov", "Brăila", "București", "Buzău", "Caraș-Severin", "Călărași", "Cluj", "Constanța", "Covasna", "Dâmbovița", "Dolj", "Galați", "Giurgiu", "Gorj", "Harghita", "Hunedoara", "Ialomița", "Iași", "Ilfov", "Maramureș", "Mehedinți", "Mureș", "Neamț", "Olt", "Prahova", "Satu Mare", "Sălaj", "Sibiu", "Suceava", "Teleorman", "Timiș", "Tulcea", "Vaslui", "Vâlcea", "Vrancea"];
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
      country:          [null, [Validators.required]],
      address:          [null, [Validators.required, Validators.minLength(2)]],
    }, 
    {
      validators: confirmPasswordValidator
    })
  }
  
  register() {
    this.errorMessages = [];
    if(this.registerForm.valid) {
      this.authService.registerUser(this.registerForm.value).subscribe({
        next: (response: any) => {
          
          this.router.navigateByUrl('/');
        },
        error: (response) => {
          console.log(response)
          this.errorMessages.push(response.error.message)
        }
      })
    }
  }
}
