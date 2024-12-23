import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AnnouncementService } from '../../../../services/announcement.service';
import { AuthService } from '../../../../services/auth.service';
import { ToastService } from '../../../../services/toast.service';
import { Router } from '@angular/router';
import { Category } from '../../../../models/category';
import { CategoryService } from '../../../../services/category.service';
import { environment } from '../../../../../environments/environment.development';

@Component({
  selector: 'app-add-advertisment-page',
  templateUrl: './add-advertisment-page.component.html',
  styleUrl: './add-advertisment-page.component.css'
})
export class AddAdvertismentPageComponent implements OnInit {
  categories: Category[] = [];
  currencies: string[] = ["LEI", "EURO"];
  imageArray: File[] = [];
  addAdForm: FormGroup = new FormGroup({});
  defaultContactName: string = "";
  errorMessages: string[] = [];
  counties: string[] = ["Alba", "Arad", "Argeș", "Bacău", "Bihor", "Bistrița-Năsăud", "Botoșani", "Brașov", "Brăila", "București", "Buzău", "Caraș-Severin", "Călărași", "Cluj", "Constanța", "Covasna", "Dâmbovița", "Dolj", "Galați", "Giurgiu", "Gorj", "Harghita", "Hunedoara", "Ialomița", "Iași", "Ilfov", "Maramureș", "Mehedinți", "Mureș", "Neamț", "Olt", "Prahova", "Satu Mare", "Sălaj", "Sibiu", "Suceava", "Teleorman", "Timiș", "Tulcea", "Vaslui", "Vâlcea", "Vrancea"];

  pondOptions = {
    class: 'my-filepond',
    multiple: true,
    labelIdle: '<u>Drag & Drop</u> or <u>Browse</u> images here!',
    acceptedFileTypes: 'image/jpeg, image/png, image/jpg',
  }

  constructor(private fb: FormBuilder, private adService: AnnouncementService, public authService: AuthService, private toastService: ToastService, private router: Router, private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.authService.user$.forEach(currentUser => {
      this.defaultContactName = currentUser?.firstName + " " + currentUser?.lastName;
    })
    this.initializeCategories()
    this.initializeFrom();
  }

  initializeFrom() {
    this. addAdForm = this.fb.group({
      title: [null, [Validators.required, Validators.minLength(16), Validators.maxLength(70), Validators.pattern(/([A-Za-z0-9]+( [A-Za-z0-9]+)+)/i)]],
      content: [null, [Validators.required, Validators.minLength(40), Validators.maxLength(9000), Validators.pattern(/([A-Za-z0-9]+( [A-Za-z0-9]+)+)/i)]],
      category: [null, [Validators.required]],
      address: [null, [Validators.required, Validators.minLength(2)]],
      county: [null, [Validators.required]],
      contactPersonName: [this.defaultContactName, [Validators.required, Validators.pattern(/([A-Za-z0-9]+( [A-Za-z0-9]+)+)/i)]],
      phoneNumber: [null, [Validators.required, Validators.pattern(/^[0-9]{10}$/g)]],
      price: [null, [Validators.required, Validators.pattern(/[0-9]/g)]],
      currency: [null, [Validators.required]],
      image: [[]]
    })
  }

  initializeCategories() {
    this.categoryService.getAllCategories().subscribe(
      categories => this.categories = categories
    )
  }

  onChange($event: any) {
    this.imageArray.push($event.file.file)
    this.addAdForm.patchValue({
      image: this.imageArray
    });
  }

  onDelete($event: any) {
    const index = this.imageArray.findIndex(i => i.lastModified === $event.file.file.lastModified && i.name === $event.file.file.name);
    this.imageArray.splice(index, 1);
    this.addAdForm.patchValue({
      image: this.imageArray
    });
  }

  addNewAd() {
    if(this.addAdForm.valid) {
      this.adService.addNewAd(this.addAdForm.value).subscribe({
        next: (response: any) => {
          this.toastService.show({title: "Anunt adauga!", message: "Anuntul a fost adaugat cu succes. Va fi publicand cand un Moderator il va apoba!", classname: "text-success"});
          this.router.navigateByUrl("anunt/" + response.url)
        },
        error: (response) => {
          this.errorMessages.push(response.error.message);
        }
      })
    }
  }

  getImage(fileName: string) {
    return environment.API_URL + "/category-imgs/" + fileName;
  }
}
