import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AddAnnouncement, Announcement } from '../models/announcement';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AnnouncementService {

  constructor(private http: HttpClient) { }

  addNewAd(ad: AddAnnouncement) {
    const headers = new HttpHeaders().append("Content-Disposition", 'multipart/form-data')
    let adData = new FormData();
    adData.append("announcement", new Blob([JSON.stringify(ad)], {type: "application/json"}));
    if(ad.image.length > 0) {
      ad.image.forEach(e => {
        adData.append("image", e);
      })
    }
    return this.http.post(environment.API_URL + "/api/Announcements/create-ad", adData, {headers});
  }

  getAnnouncements() {
    return this.http.get<Announcement[]>(environment.API_URL + "/api/Announcements/get-all-ads");
  }

  getAnnouncementByUrl(url: string) {
    return this.http.get<Announcement>(environment.API_URL + "/api/Announcements/get-ad-by-url/" + url);
  }

  deleteAnnouncement(id: number) {
    return this.http.delete(environment.API_URL + "/api/Announcements/delete-ad/" + id);
  }

}
