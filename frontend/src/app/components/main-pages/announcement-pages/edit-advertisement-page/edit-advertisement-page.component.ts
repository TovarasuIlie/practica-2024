import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AnnouncementService } from '../../../../services/announcement.service';
import { AuthService } from '../../../../services/auth.service';
import { ToastService } from '../../../../services/toast.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Announcement } from '../../../../models/announcement';
import { Category } from '../../../../models/category';
import { CategoryService } from '../../../../services/category.service';
import { environment } from '../../../../../environments/environment.development';

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
  categories: Category[] = [];
  imageArray: File[] = [];

  pondOptions = {
    class: 'my-filepond',
    multiple: true,
    labelIdle: '<u>Drag & Drop</u> or <u>Browse</u> images here!',
    acceptedFileTypes: 'image/jpeg, image/png, image/jpg',
  }

  constructor(private fb: FormBuilder, private adService: AnnouncementService, public authService: AuthService, private toastService: ToastService, private router: Router,
              private activatedRoute: ActivatedRoute, private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.initializeCategories();
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

  initializeCategories() {
    this.categoryService.getAllCategories().subscribe(
      categories => this.categories = categories
    )
  }

  initializeForm() {
    this. editAdForm = this.fb.group({
      title: [this.currentAd.title, [Validators.required, Validators.minLength(16), Validators.maxLength(70), Validators.pattern(/([A-Za-z0-9]+( [A-Za-z0-9]+)+)/i)]],
      content: [this.currentAd.content, [Validators.required, Validators.minLength(40), Validators.maxLength(9000), Validators.pattern(/([A-Za-z0-9]+( [A-Za-z0-9]+)+)/i)]],
      address: [this.currentAd.address, [Validators.required]],
      contactPersonName: [this.currentAd.contactPersonName, [Validators.required, Validators.pattern(/([A-Za-z0-9]+( [A-Za-z0-9]+)+)/i)]],
      phoneNumber: [this.currentAd.phoneNumber, [Validators.required]],
      price: [this.currentAd.price, [Validators.required, Validators.pattern(/[0-9]/)]],
      currency: [this.currentAd.currency, [Validators.required]],
    })
  }

  editCurrentAd() {
    if(this.editAdForm.valid) {
      this.adService.updateAnnouncement(this.currentAd.id, this.editAdForm.value).subscribe({
        next: (value) => {
          this.toastService.show({title: "Anunt editat!", message: "Anuntul a fost editat cu succes!", classname: "text-success"});
          this.router.navigateByUrl("/anunt/" + value.url);
        }
      })
    } else {

    }
  }
  
  getImage(fileName: string) {
    return environment.API_URL + "/category-imgs/" + fileName;
  }
}
