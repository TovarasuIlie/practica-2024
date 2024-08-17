import { Component, ElementRef, Inject, OnInit, QueryList, Renderer2, ViewChildren, ɵsetClassMetadataAsync } from '@angular/core';
import { User, UserAdmin } from '../../../../models/user';
import { DOCUMENT } from '@angular/common';
import { AuthService } from '../../../../services/auth.service';
import { UserManagementService } from '../../../../services/user-management.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../../services/toast.service';
import { HttpErrorResponse } from '@angular/common/http';
import { __values } from 'tslib';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { SuspendDays } from '../../../../models/suspend';
import { SuspendAccountsService } from '../../../../services/suspend-accounts.service';
import { toInteger } from '@ng-bootstrap/ng-bootstrap/util/util';

@Component({
  selector: 'app-user-details-page',
  templateUrl: './user-details-page.component.html',
  styleUrl: './user-details-page.component.css'
})
export class UserDetailsPageComponent implements OnInit {
  user: UserAdmin = <UserAdmin>{};
  editUserForm: FormGroup = new FormGroup({});
  suspendUserForm: FormGroup = new FormGroup({});
  errorMessages: string[] = [];
  matcher = new ErrorStateMatcher();
  @ViewChildren('closeModal') closeModal!: QueryList<ElementRef>
  counties: string[] = ['Alba', 'Arad', 'Arges', 'Bacau', 'Bihor', 'Bistrita-Nasaud'];
  suspendDays: SuspendDays[] = [
    {
      text: "O zi",
      numberOfDays: 1
    },
    {
      text: "2 zile",
      numberOfDays: 2
    },
    {
      text: "3 zile",
      numberOfDays: 3
    },
    {
      text: "O saptamana",
      numberOfDays: 7
    },
    {
      text: "Permanent",
      numberOfDays: 0
    }
  ]

  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, public authService: AuthService, private userManageService: UserManagementService, 
              private activatedRoute: ActivatedRoute, private toastService: ToastService, private router: Router, private fb: FormBuilder, private suspendAccService: SuspendAccountsService) {
    const link = this._renderer2.createElement('link');
    link.href = "/assets/dashboard/css/style.css";
    link.rel = "stylesheet"
    this._renderer2.appendChild(this._document.head, link);
  }

  ngOnInit(): void {
    this.initializeUser();
    this.initializeForm();
  }

  initializeUser() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.ad instanceof HttpErrorResponse)) {
        this.user = response.user;
      } else {
        this.user = {} as UserAdmin;
      }
    })
  }

  initializeForm() {
    this.editUserForm = this.fb.group({
      username: [this.user.username, [Validators.required]],
      email: [this.user.email, [Validators.required]],
      firstName: [this.user.firstName, [Validators.required]],
      lastName: [this.user.lastName, [Validators.required]],
      address: [this.user.address, [Validators.required]]
    });
    this.suspendUserForm = this.fb.group({
      reason: [null, [Validators.required]],
      suspendDays: [null, [Validators.required, Validators.minLength(1)]]
    })
  }

  refreshUser() {
    this.userManageService.getSpecificUser(this.user.id).subscribe({
      next: (value) => {
        this.user = value;
      }
    })
  }

  forceConfirmEmail() {
    this.userManageService.forceConfirmEmail(this.user.id).subscribe({
      next: _ => {
        this.refreshUser();
        this.toastService.show({title: "Email Confirmat", message: "Email-ul a fost marcat ca si verificat!", classname: "text-success"});
      }
    })
  }

  deleteUser() {
    if(this.user.id != this.authService.getID()) {
      this.userManageService.deleteUser(this.user.id).subscribe({
        next: _ => {
          this.toastService.show({title: "Cont Sters", message: "Contul a fost sters cu succes!", classname: "text-success"});
        }
      })
    } else {
      this.toastService.show({title: "Eroare stergere cont!", message: "Nu poti sa iti stergi propriul cont!", classname: "text-danger"});
    }
  }


  editUser() {
    this.errorMessages = [];
    if(this.editUserForm.valid) {
      this.userManageService.editUser(this.user.id, this.editUserForm.value).subscribe({
        next: _ => {
          this.refreshUser();
          this.toastService.show({title: "Cont Sters", message: "Contul a fost sters cu succes!", classname: "text-success"});
        },
        error: (response) => {
          console.log(response);
        }
      })
    } else {
      this.errorMessages.push("Toate campuriile trebuie completate!");
    }
  }

  suspendUser() {
    this.errorMessages = []
    if(this.suspendUserForm.valid) {
      this.suspendAccService.suspendAccount(this.user.id, this.suspendUserForm.value.reason, this.suspendUserForm.value.suspendDays).subscribe({
        next: (value: any) => {
          this.refreshUser();
          this.closeModal.forEach(x => x.nativeElement.click());
          this.toastService.show({title: "Cont suspendat", message: value.message, classname: "text-success"});
        }, 
        error: (response) => {
          console.log(response);
          this.errorMessages.push(response.error.message);
        }
      })
    } else {
      this.errorMessages.push("Toate campurile trebuie completate!")
    }
  }

  unsuspendUser() {
    this.suspendAccService.unsuspendAccount(this.user.suspendDetails.id.toString()).subscribe({
      next: (value: any) => {
        this.refreshUser();
        this.closeModal.forEach(x => x.nativeElement.click());
        this.toastService.show({title: "Suspendare scoasa", message: value.message, classname: "text-success"});
      },
      error: (response) => {
        console.log(response);
        this.closeModal.forEach(x => x.nativeElement.click());
        this.toastService.show({title: "Cont suspendat", message: response.error.message, classname: "text-danger"});
      }
    })
  }
}
