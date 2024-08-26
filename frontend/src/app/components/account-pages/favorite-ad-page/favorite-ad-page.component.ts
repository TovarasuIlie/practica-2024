import { Component, OnInit } from '@angular/core';
import { Announcement } from '../../../models/announcement';
import { FavoriteAdService } from '../../../services/favorite-ad.service';
import { ToastService } from '../../../services/toast.service';
import { environment } from '../../../../environments/environment.development';

@Component({
  selector: 'app-favorite-ad-page',
  templateUrl: './favorite-ad-page.component.html',
  styleUrl: './favorite-ad-page.component.css'
})
export class FavoriteAdsPageComponent implements OnInit {
  ads: Announcement[] = [];

  constructor(private favoriteService: FavoriteAdService, private toastService: ToastService) {}
  ngOnInit(): void {
    this.initializeAds();
  }

  initializeAds() {
    this.favoriteService.getAllFavoriteAds().subscribe((ads: any) => {
      this.ads = ads;
    })
  }

  getImage(fileName: string, index: string) {
    return environment.API_URL + "/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }

  refreshAd() {
    this.initializeAds();
  }
}
