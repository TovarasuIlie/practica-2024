import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FavoriteAdService } from '../../../services/favorite-ad.service';
import { Announcement } from '../../../models/announcement';
import { ToastService } from '../../../services/toast.service';
import { AnnouncementService } from '../../../services/announcement.service';

@Component({
  selector: 'app-favorite-button',
  templateUrl: './favorite-button.component.html',
  styleUrl: './favorite-button.component.css'
})
export class FavoriteButtonComponent implements OnChanges {
  isAddedToFavorite: any = false;
  @Input("current-ad") currentAd!: Announcement
  
  constructor(private favoriteService: FavoriteAdService, private toastService: ToastService, private adService: AnnouncementService) {}

  ngOnChanges(changes: SimpleChanges): void {
    this.currentAd = changes['currentAd'].currentValue;
    if(this.currentAd) {
      setTimeout(() => {
        this.checkFavorite();
      }, 500)
    }
  }

  checkFavorite() {
    this.favoriteService.checkFavorite(this.currentAd.id).subscribe({
      next: (value) => {
        this.isAddedToFavorite = value;
      }
    })
  }

  addToFavorite() {
    this.favoriteService.addToFavorite(this.currentAd.id).subscribe({
      next: _ => {
        this.isAddedToFavorite = true;
        this.toastService.show({title: "Adaugat la favorite!", message: 'Anuntul "' + this.currentAd.title + '" a fost adaugat la favorite!', classname: "text-success"});
      }
    })
  }

  removeFromFavorite() {
    this.favoriteService.removeFromFavorite(this.currentAd.id).subscribe({
      next: _ => {
        this.isAddedToFavorite = false;
        this.toastService.show({title: "Scos la favorite!", message: 'Anuntul "' + this.currentAd.title + '" a fost scos din lista de favorite!', classname: "text-success"});
      }
    })
  }
}
