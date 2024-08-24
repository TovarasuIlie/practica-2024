import { Component, ElementRef, QueryList, ViewChildren } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Report } from '../../../models/report';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../../services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../../../models/user';
import { ErrorStateMatcher } from '@angular/material/core';
import { AccountService } from '../../../services/account.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css'
})
export class ProfilePageComponent {
  myReports: Report[] = [];
  editUserForm: FormGroup = new FormGroup({})
  user!: User | null;
  matcher = new ErrorStateMatcher();
  errorMessages: string[] = [];
  @ViewChildren("closeModal") closeModal!: QueryList<ElementRef>

  constructor(private activatedRoute: ActivatedRoute, public authService: AuthService, private fb: FormBuilder, private accountService: AccountService) {}

  ngOnInit(): void {
    this.initializeReports();
    this.initializeUser();
    this.initializeForm();
  }

  initializeReports() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.myReports instanceof HttpErrorResponse)) {
        this.myReports = response.myReports;
      } else {
        this.myReports = {} as Report[];
      }
    })
  }

  initializeUser() {
    this.authService.user$.subscribe(
      account => this.user = account
    )
  }

  initializeForm() {
    this.editUserForm = this.fb.group({
      firstName: [this.user?.firstName, [Validators.required]],
      lastName: [this.user?.lastName, [Validators.required]],
      address: [this.user?.address, [Validators.required]]
    });
  }

  editUser() {
    if(this.editUserForm.valid) {
      this.accountService.updateProfile(this.editUserForm.value).subscribe({
        next: (value) => {
          this.user = value;
          this.closeModal.forEach(x => x.nativeElement.click());
        }
      })
    }
  }
}
