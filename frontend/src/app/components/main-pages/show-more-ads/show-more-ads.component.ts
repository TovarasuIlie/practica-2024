import { Component, OnInit } from '@angular/core';
import { AnnouncementManageService } from '../../../services/announcement-manage.service';
import { AnnouncementService } from '../../../services/announcement.service';
import { Announcement } from '../../../models/announcement';
import { AuthService } from '../../../services/auth.service';
import { environment } from '../../../../environments/environment.development';

@Component({
  selector: 'app-show-more-ads',
  templateUrl: './show-more-ads.component.html',
  styleUrl: './show-more-ads.component.css'
})
export class ShowMoreAdsComponent implements OnInit {
  ads: Announcement[] = [];
  loadingAds: boolean = true;

  constructor(private adService: AnnouncementService, public authService: AuthService) {}

  ngOnInit(): void {
    this.initializeAds();
  }

  initializeAds() {
    this.adService.getAnnouncements().subscribe((ads: any) => {
      this.ads = ads;
      this.loadingAds = false;
    })
  }

  getImage(fileName: string, index: string) {
    return environment.API_URL + "/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }
}
