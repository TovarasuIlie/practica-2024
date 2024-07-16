import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.css'
})
export class RegisterPageComponent {
  registerForm: FormGroup = new FormGroup({});
  counties: string[] = ['Alba', 'Arad', 'Arges', 'Bacau', 'Bihor', 'Bistrita-Nasaud'];
  matcher = new ErrorStateMatcher();

  constructor(private fb: FormBuilder) {

  }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.registerForm = this.fb.group({
      firstName:        [null, [Validators.required]],
      lastName:         [null, [Validators.required]],
      email:            [null, [Validators.required, Validators.email]],
      password:         [null, [Validators.required, Validators.minLength(8), Validators.pattern(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/)]],
      confirmPassword:  [null, [Validators.required, Validators.minLength(8)]],
      county:           [null, [Validators.required]]
    })
  }
  
  register() {
    if(this.registerForm.valid) {
      console.log(this.registerForm.getRawValue());
    }
  }
}
