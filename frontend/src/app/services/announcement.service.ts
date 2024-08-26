import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AddAnnouncement, Announcement, EditAnnouncement } from '../models/announcement';
import { environment } from '../../environments/environment.development';
import { delay } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnnouncementService {

  constructor(private http: HttpClient) { }

  addNewAd(ad: AddAnnouncement) {
    const headers = new HttpHeaders().append("Content-Disposition", 'multipart/form-data')
    ad.address = ad.county + ", " + ad.address;
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

  getMyAnnouncements() {
    return this.http.get<Announcement[]>(environment.API_URL + "/api/Announcements/get-my-ads")
  }

  getAnnouncementByUrl(url: string) {
    return this.http.get<Announcement>(environment.API_URL + "/api/Announcements/get-ad-by-url/" + url);
  }

  getAnnouncementsByCategory(url: string) {
    return this.http.get<Announcement[]>(environment.API_URL + "/api/Announcements/get-ads-by-category/" + url);
  }

  getAnnouncementsByUser(username: string) {
    return this.http.get<Announcement[]>(environment.API_URL + "/api/Announcements/get-ads-for-user/" + username);
  }

  getAnnouncementsByTitle(title: string) {
    return this.http.get<Announcement[]>(environment.API_URL + "/api/Announcements/search-for-ads?title=" + title);
  }

  getAnnouncementById(id: string) {
    return this.http.get<Announcement>(environment.API_URL + "/api/Announcements/get-ad-by-id/" + id);
  }

  updateAnnouncement(id: number, editedAd: EditAnnouncement) {
    return this.http.put<Announcement>(environment.API_URL + "/api/Announcements/edit-ad/" + id, editedAd);
  }

  deleteAnnouncement(id: number) {
    return this.http.delete(environment.API_URL + "/api/Announcements/delete-ad/" + id);
  }

  activateAnnouncement(id: number) {
    return this.http.get(environment.API_URL + "/api/Announcements/activate/" + id);
  }

  deactivateAnnouncement(id: number) {
    return this.http.get(environment.API_URL + "/api/Announcements/deactivate/" + id);
  }

}
