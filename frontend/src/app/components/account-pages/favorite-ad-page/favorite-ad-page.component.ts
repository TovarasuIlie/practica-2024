import { Component, OnInit } from '@angular/core';
import { Announcement } from '../../../models/announcement';
import { AnnouncementService } from '../../../services/announcement.service';
import { CategoryService } from '../../../services/category.service';

@Component({
  selector: 'app-favorite-ad-page',
  templateUrl: './favorite-ad-page.component.html',
  styleUrl: './favorite-ad-page.component.css'
})
export class FavoriteAdsPageComponent implements OnInit {
  ads: Announcement[] = [];

  constructor(private adService: AnnouncementService, private categoryService: CategoryService) {}
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
