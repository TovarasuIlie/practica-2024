import { Component, OnInit } from '@angular/core';
import { AnnouncementService } from '../../../../services/announcement.service';
import { Announcement } from '../../../../models/announcement';
import { AuthService } from '../../../../services/auth.service';
import { environment } from '../../../../../environments/environment.development';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-ads-list-by-category',
  templateUrl: './ads-list-by-category.component.html',
  styleUrl: './ads-list-by-category.component.css'
})
export class AdsListByCategoryComponent implements OnInit {
  ads: Announcement[] = [];
  loadingAds: boolean = true;
  constructor(private adService: AnnouncementService, public authService: AuthService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.initializeAds();
  }

  initializeAds() {
    this.adService.getAnnouncementsByCategory(this.activatedRoute.snapshot.params["categorySearchLink"]).subscribe((ads: any) => {
      this.ads = ads;
      this.loadingAds = false;
    })
  }

  getImage(fileName: string, index: string) {
    return environment.API_URL + "/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }
}
