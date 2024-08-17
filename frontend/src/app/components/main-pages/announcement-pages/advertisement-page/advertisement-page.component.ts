import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Announcement } from '../../../../models/announcement';
import { AuthService } from '../../../../services/auth.service';
import { AnnouncementService } from '../../../../services/announcement.service';
import { ToastService } from '../../../../services/toast.service';
import { ErrorStateMatcher } from '@angular/material/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReportService } from '../../../../services/report.service';

@Component({
  selector: 'app-advertisement-page',
  templateUrl: './advertisement-page.component.html',
  styleUrl: './advertisement-page.component.css'
})
export class AdvertisementPageComponent implements OnInit {
  currentAd: Announcement = {} as Announcement;
  loadingAd: boolean = true;
  loadingEditButton: boolean = false;
  @ViewChild("closeModal") closeModal!: ElementRef;
  callSeller: string = "Suna Vanzatorul";
  errorMessages: string[] = [];
  matcher = new ErrorStateMatcher();
  reportForm: FormGroup = new FormGroup({});

  constructor(private activatedRoute: ActivatedRoute, public authService: AuthService, private adService: AnnouncementService, private toastService: ToastService, private router: Router,
              private fb: FormBuilder, private reportService: ReportService
  ) {}

  ngOnInit(): void {
    this.initializeAd();
    this.initializeForm();
  }

  initializeAd() {
    this.adService.getAnnouncementByUrl(this.activatedRoute.snapshot.params['adTitle']).subscribe(ad => {
      this.currentAd = ad;
      this.loadingAd = false;
    })
  }

  initializeForm() {
    this.reportForm = this.fb.group({
      announcementId: [this.currentAd.id],
      message: [null, [Validators.required]],
    })
  }

  deleteAd() {
    this.adService.deleteAnnouncement(this.currentAd.id).subscribe({
      next: _ => {
        this.closeModal.nativeElement.click();
        this.toastService.show({title: "Anunt sters", message: "Anuntul a fost sters cu succes!", classname: "text-success"});
        this.router.navigateByUrl("/");
      }
    })
  }

  getImage(fileName: string, index: string) {
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }

  changeText() {
    if(this.authService) {
      this.callSeller = this.currentAd.phoneNumber;
    } else {
      this.toastService.show({title: "Eroare", message: "Nu poti sa vezi numarul de telefon, daca nu esti logat.", classname: "text-danger"})
    }
  }

  reportAd() {
    if(this.reportForm.valid) {
      this.reportService.insertNewReport(this.reportForm.value).subscribe({
        next: (value) => {
          console.log(value);
        },
        error: (response) => {
          console.log(response);
        }
      })
    } else {
      this.errorMessages.push("Toate campurile trebuie completate!");
    }
  }
}
