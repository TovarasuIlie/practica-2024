import { Component, OnInit } from '@angular/core';
import { Announcement } from '../../../models/announcement';
import { AnnouncementService } from '../../../services/announcement.service';

@Component({
  selector: 'app-index-page',
  templateUrl: './index-page.component.html',
  styleUrl: './index-page.component.css'
})
export class IndexPageComponent implements OnInit {
  ads: Announcement[] = [];

  constructor(private adService: AnnouncementService) {}
  
  ngOnInit(): void {
    // this.initializeAds();
  }

  initializeAds() {
    this.adService.getAnnouncements().subscribe((ads: any) => {
      this.ads = ads;
    })
  }
}
