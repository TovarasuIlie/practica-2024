import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Announcement } from '../models/announcement';

@Injectable({
  providedIn: 'root'
})
export class FavoriteAdService {

  constructor(private http: HttpClient) { }

  addToFavorite(adID: number) {
    return this.http.get(environment.API_URL + "/api/Wishlist/add-to-wishlist/" + adID);
  }

  removeFromFavorite(adID: number) {
    return this.http.delete(environment.API_URL + "/api/Wishlist/remove-from-wishlist/" + adID);
  }

  checkFavorite(adID: number) {
    return this.http.get(environment.API_URL + "/api/Wishlist/check-wishlist/" + adID);
  }

  getAllFavoriteAds() {
    return this.http.get<Announcement[]>(environment.API_URL + "/api/Wishlist/get-wishlist");
  }
}
