import { Component, OnInit } from '@angular/core';
import { AnnouncementService } from '../../../../services/announcement.service';
import { Announcement } from '../../../../models/announcement';

@Component({
  selector: 'app-ads-list-by-category',
  templateUrl: './ads-list-by-category.component.html',
  styleUrl: './ads-list-by-category.component.css'
})
export class AdsListByCategoryComponent implements OnInit {
  ads: Announcement[] = [];
  constructor(private adService: AnnouncementService) {}

  ngOnInit(): void {
    this.initializeAds();
  }

  initializeAds() {
    this.adService.getAnnouncements().subscribe((ads: any) => {
      this.ads = ads;
    })
  }

  getImage(fileName: string, index: string) {
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }
}
