import { Component, OnInit } from '@angular/core';
import { Announcement } from '../../../models/announcement';
import { FavoriteAdService } from '../../../services/favorite-ad.service';
import { ToastService } from '../../../services/toast.service';

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
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }

  removeFromFavorite(id: number) {
    this.favoriteService.removeFromFavorite(id).subscribe({
      next: _ => {
        this.initializeAds();
        this.toastService.show({title: "Scos de la favorite!", message: 'Anuntul a fost scos din lista de favorite!', classname: "text-success"});
      }
    })
  }
}
