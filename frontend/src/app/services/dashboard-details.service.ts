import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DashboardDetails } from '../models/dashboard-details';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class DashboardDetailsService {

  constructor(private http: HttpClient) { }

  getAllDetails() {
    return this.http.get<DashboardDetails>(environment.API_URL + "/api/DashboardDetails/get-all-details");
  }
}
