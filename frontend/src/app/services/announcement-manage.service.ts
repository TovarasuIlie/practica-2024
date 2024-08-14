import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Announcement } from '../models/announcement';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AnnouncementManageService {

  constructor(private http: HttpClient) { }

  getAllAds() {
    return this.http.get<Announcement[]>(environment.API_URL + "/api/Announcements-management/get-all-ads");
  }

  getAdById(id: string) {
    return this.http.get<Announcement>(environment.API_URL + "/api/Announcements-management/get-ad-by-id/" + id);
  }

  markAsApproved(id: number) {
    return this.http.get(environment.API_URL + "/api/Announcements-management/approve/" + id);
  }

  markAsRejected(id: number) {
    return this.http.get(environment.API_URL + "/api/Announcements-management/reject/" + id);
  }

  deleteAd(id: number) {
    return this.http.delete(environment.API_URL + "/api/Announcements-management/delete-ad/" + id);
  }
}
