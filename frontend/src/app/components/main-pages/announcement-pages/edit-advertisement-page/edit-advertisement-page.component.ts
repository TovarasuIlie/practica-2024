import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AnnouncementService } from '../../../../services/announcement.service';
import { AuthService } from '../../../../services/auth.service';
import { ToastService } from '../../../../services/toast.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Announcement } from '../../../../models/announcement';

@Component({
  selector: 'app-edit-advertisement-page',
  templateUrl: './edit-advertisement-page.component.html',
  styleUrl: './edit-advertisement-page.component.css'
})
export class EditAdvertisementPageComponent {
  editAdForm: FormGroup = new FormGroup({});
  currencies: string[] = ["LEI", "EURO"];
  id!: string;
  currentAd!: Announcement;

  constructor(private fb: FormBuilder, private adService: AnnouncementService, public authService: AuthService, private toastService: ToastService, private router: Router,
              private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.initializeAd();
    this.initializeForm();
  }

  initializeAd() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.ad instanceof HttpErrorResponse)) {
        this.currentAd = response.ad;
      } else {
        this.currentAd = {} as Announcement;
      }
    })
  }

  initializeForm() {
    this. editAdForm = this.fb.group({
      title: [this.currentAd.title, [Validators.required, Validators.minLength(16), Validators.maxLength(70)]],
      content: [this.currentAd.content, [Validators.required, Validators.minLength(40), Validators.maxLength(9000)]],
      address: [this.currentAd.address, [Validators.required]],
      contactPersonName: [this.currentAd.contactPersonName, [Validators.required]],
      phoneNumber: [this.currentAd.phoneNumber, [Validators.required]],
      price: [this.currentAd.price, [Validators.required, Validators.pattern(/[0-9]/)]],
      currency: [this.currentAd.currency, [Validators.required]],
    })
  }

  editCurrentAd() {
    if(this.editAdForm.valid) {
      this.adService.updateAnnouncement(this.currentAd.id, this.editAdForm.value).subscribe({
        next: _ => {
          this.toastService.show({title: "Anunt editat!", message: "Anuntul a fost editat cu succes!", classname: "text-success"});
          this.router.navigateByUrl("/anunt/" + this.currentAd.url);
        }
      })
    } else {

    }
  }
}
