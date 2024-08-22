import { DOCUMENT } from '@angular/common';
import { Component, ElementRef, Inject, OnInit, QueryList, Renderer2, ViewChildren } from '@angular/core';
import { AuthService } from '../../../../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../../services/toast.service';
import { FormBuilder } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Announcement } from '../../../../models/announcement';
import { AnnouncementManageService } from '../../../../services/announcement-manage.service';

@Component({
  selector: 'app-ad-details-page',
  templateUrl: './ad-details-page.component.html',
  styleUrl: './ad-details-page.component.css'
})
export class AdDetailsPageComponent implements OnInit {
  ad: Announcement = <Announcement>{}
  @ViewChildren('closeModal') closeModal!: QueryList<ElementRef>

  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, public authService: AuthService, private adManageService: AnnouncementManageService,
              private activatedRoute: ActivatedRoute, private toastService: ToastService, private router: Router, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initializeAd()
  }

  initializeAd() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.ad instanceof HttpErrorResponse)) {
        this.ad = response.ad;
      } else {
        this.ad = {} as Announcement;
      }
    })
  }

  refreshAd() {
    this.adManageService.getAdById(this.ad.id.toString()).subscribe(ad => this.ad = ad);
  }

  getImage(fileName: string, index: string) {
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }

  deleteAd() {
    this.adManageService.deleteAd(this.ad.id).subscribe({
      next: (value: any) => {
        this.closeModal.forEach(x => x.nativeElement.click())
        this.toastService.show({title: "Anunt sters", message: "Anuntul a fost sters cu succes!", classname: "text-success"});
        this.router.navigateByUrl("/dashboard/management-anunturi");
      },
      error: (response) => {
        console.log(response)
      }
    })
  }

  approveAd() {
    this.adManageService.markAsApproved(this.ad.id).subscribe({
      next: (value: any) => {
        this.closeModal.forEach(x => x.nativeElement.click())
        this.refreshAd();
        this.toastService.show({title: "Anunt aprobat", message: value.message, classname: "text-success"})
      },
      error: (response) => {
        console.log(response)
      }
    })
  }

  rejectAd() {
    this.adManageService.markAsRejected(this.ad.id).subscribe({
      next: (value: any) => {
        this.closeModal.forEach(x => x.nativeElement.click())
        this.refreshAd();
        this.toastService.show({title: "Anunt respins", message: value.message, classname: "text-success"})
      },
      error: (response) => {
        console.log(response)
      }
    })
  }
}
