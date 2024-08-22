import { Component, OnInit } from '@angular/core';
import { AnnouncementManageService } from '../../../services/announcement-manage.service';
import { AnnouncementService } from '../../../services/announcement.service';
import { Announcement } from '../../../models/announcement';

@Component({
  selector: 'app-show-more-ads',
  templateUrl: './show-more-ads.component.html',
  styleUrl: './show-more-ads.component.css'
})
export class ShowMoreAdsComponent implements OnInit {
  ads: Announcement[] = [];
  loadingAds: boolean = true;

  constructor(private adService: AnnouncementService) {}

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
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }
}
