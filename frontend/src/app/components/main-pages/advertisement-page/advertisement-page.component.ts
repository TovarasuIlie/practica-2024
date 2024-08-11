import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Announcement } from '../../../models/announcement';
import { AuthService } from '../../../services/auth.service';
import { AnnouncementService } from '../../../services/announcement.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-advertisement-page',
  templateUrl: './advertisement-page.component.html',
  styleUrl: './advertisement-page.component.css'
})
export class AdvertisementPageComponent implements OnInit {
  currentAd: Announcement = {} as Announcement;
  @ViewChild("closeModal") closeModal!: ElementRef

  constructor(private activatedRoute: ActivatedRoute, public authService: AuthService, private adService: AnnouncementService, private toastService: ToastService, private router: Router) {}

  ngOnInit(): void {
    this.initializeAd()
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
}
