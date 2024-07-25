import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AuthService } from '../../../../services/auth.service';
import { Router } from '@angular/router';
import { ToastService } from '../../../../services/toast.service';

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

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private toastService: ToastService) {

  }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.registerForm = this.fb.group({
      firstName:        [null, [Validators.required]],
      lastName:         [null, [Validators.required]],
      username:         [null, [Validators.required]],
      email:            [null, [Validators.required, Validators.email]],
      password:         [null, [Validators.required, Validators.minLength(8), Validators.pattern(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/)]],
      confirmPassword:  [null, [Validators.required, Validators.minLength(8)]],
      county:           [null, [Validators.required]]
    })
  }
  
  register() {
    this.errorMessages = [];
    if(this.registerForm.valid) {
      this.authService.registerUser(this.registerForm.value).subscribe({
        next: (response: any) => {
          console.log(response)
          this.router.navigateByUrl("/");
          this.toastService.show({title: "Cont creat cu succes!", message: response.message, classname: "text-success"});
        },
        error: (response) => {
          this.errorMessages.push(response.error.message)
          console.log(response)
        }
      })
    }
  }
}
