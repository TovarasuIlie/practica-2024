import { Component, ElementRef, QueryList, ViewChildren } from '@angular/core';
import { Announcement } from '../../../models/announcement';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../../environments/environment.development';
import { AnnouncementService } from '../../../services/announcement.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-index-page',
  templateUrl: './index-page.component.html',
  styleUrl: './index-page.component.css'
})
export class IndexPageComponent {
  myAds: Announcement[] = [];
  activeAds: Announcement[] = [];
  waitingAds: Announcement[] = [];
  disabledAds: Announcement[] = [];
  @ViewChildren("closeModal") closeModal!: QueryList<ElementRef>
  adId!: number;
  
  constructor(private activatedRoute: ActivatedRoute, private adService: AnnouncementService, private toastService: ToastService) {}

  ngOnInit(): void {
    this.initializeAds();
  }

  initializeAds() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.myAds instanceof HttpErrorResponse)) {
        this.activeAds = response.myAds.filter((x: Announcement) =>  x.approved && !x.deactivated);
        this.waitingAds = response.myAds.filter((x: Announcement) => !x.approved && !x.deactivated);
        this.disabledAds = response.myAds.filter((x: Announcement) => x.deactivated);
      } else {
        this.activeAds = {} as Announcement[];
        this.waitingAds = {} as Announcement[];
        this.disabledAds = {} as Announcement[];
      }
    })
  }

  refreshAds() {
    this.adService.getMyAnnouncements().subscribe(myAds => {
      this.activeAds = myAds.filter((x: Announcement) =>  x.approved && !x.deactivated);
      this.waitingAds = myAds.filter((x: Announcement) => !x.approved && !x.deactivated);
      this.disabledAds = myAds.filter((x: Announcement) => x.deactivated);
    })
  }

  getImage(fileName: string, index: string) {
    return environment.API_URL + "/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }

  activateAd(id: number) {
    this.adService.activateAnnouncement(id).subscribe({
      next: _ => {
        this.refreshAds();
        this.toastService.show({title: "Status anunt!", message: "Anuntul a fost activat cu succes. Asteapta raspunsul unui Moderator, care iti v-a publica anuntul.", classname: "text-success"});
      }
    })
  }

  deactivateAd(id: number) {
    this.adService.deactivateAnnouncement(id).subscribe({
      next: _ => {
        this.refreshAds();
        this.toastService.show({title: "Status anunt!", message: "Anuntul a fost dezactivat cu succes.", classname: "text-success"});
      }
    })
  }

  setId(id: number) {
    this.adId = id;
  }

  deleteAd() {
    this.adService.deleteAnnouncement(this.adId).subscribe({
      next: _ => {
        this.closeModal.forEach(element => {
          element.nativeElement.click();
        });
        this.refreshAds();
        this.toastService.show({title: "Anunt sters", message: "Anuntul a fost sters cu succes!", classname: "text-success"});
        this.adId = 0;
      }
    })
  }
}
