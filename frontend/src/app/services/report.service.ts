import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Report, ReportInsert } from '../models/report';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient) { }

  insertNewReport(newReport: ReportInsert) {
    return this.http.post(environment.API_URL + "/api/Reports/create-report", newReport)
  }

  getMyReports() {
    return this.http.get<Report[]>(environment.API_URL + "/api/Reports/my-reports");
  }

  getAllReports() {
    return this.http.get<Report[]>(environment.API_URL + "/api/Reports-management/get-reports");
  }

  getReportById(id: number) {
    return this.http.get<Report>(environment.API_URL + "/api/Reports-management/get-report/" + id);
  }
}
