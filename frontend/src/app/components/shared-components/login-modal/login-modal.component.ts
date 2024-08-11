import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AuthService } from '../../../services/auth.service';
import { ToastService } from '../../../services/toast.service';
import { Router } from '@angular/router';

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

  constructor(private fb: FormBuilder, private authService: AuthService, private toastService: ToastService, private router: Router) {

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
          this.toastService.show({title: "Esti Autentificat", message: "Bun venit, te-ai autentificat cu succes!", classname: "text-success"});
          this.closeModal.nativeElement.click();
          this.router.navigateByUrl("/");
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
