import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-login-modal',
  templateUrl: './login-modal.component.html',
  styleUrl: './login-modal.component.css'
})
export class LoginModalComponent implements OnInit {
  loginForm: FormGroup = new FormGroup({});
  matcher = new ErrorStateMatcher();
  errorMessages: string[] = [];
  @ViewChild('closeModal') closeModal!: ElementRef;

  constructor(private fb: FormBuilder, private authService: AuthService) {

  }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.loginForm = this.fb.group({
      username:    [null, [Validators.required]],
      password: [null, [Validators.required, Validators.minLength(8)]]
    })
  }
  
  login() {
    this.errorMessages = [];
    if(this.loginForm.valid) {
      this.authService.loginUser(this.loginForm.getRawValue()).subscribe({
        next: (response: any) => {
          console.log(response)
          this.closeModal.nativeElement.click();
        },
        error: (response) => {
          this.errorMessages.push(response.error.message);
        }
      })
    }
  }

  resetForm() {
    this.loginForm.reset();
  }
}
