import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AnnouncementService } from '../../../../services/announcement.service';
import { AuthService } from '../../../../services/auth.service';
import { ToastService } from '../../../../services/toast.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-advertisment-page',
  templateUrl: './add-advertisment-page.component.html',
  styleUrl: './add-advertisment-page.component.css'
})
export class AddAdvertismentPageComponent implements OnInit {
  foods: string[] = ['Steak', 'Pizza', 'Tacos'];
  currencies: string[] = ["LEI", "EURO"];
  imageArray: File[] = [];
  addAdForm: FormGroup = new FormGroup({});

  pondOptions = {
    class: 'my-filepond',
    multiple: true,
    labelIdle: '<u>Drag & Drop</u> or <u>Browse</u> images here!',
    acceptedFileTypes: 'image/jpeg, image/png, image/jpg',
  }

  constructor(private fb: FormBuilder, private adService: AnnouncementService, public authService: AuthService, private toastService: ToastService, private router: Router) {}

  ngOnInit(): void {
    this.initializeFrom();
  }

  initializeFrom() {
    this. addAdForm = this.fb.group({
      title: [null, [Validators.required, Validators.minLength(16), Validators.maxLength(70)]],
      content: [null, [Validators.required, Validators.minLength(40), Validators.maxLength(9000)]],
      address: [null, [Validators.required]],
      contactPersonName: [null, [Validators.required]],
      phoneNumber: [null, [Validators.required]],
      price: [null, [Validators.required, Validators.pattern(/[0-9]/)]],
      currency: [null, [Validators.required]],
      image: [[]]
    })
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
          console.log(response);
        }
      })
    }
  }
}
