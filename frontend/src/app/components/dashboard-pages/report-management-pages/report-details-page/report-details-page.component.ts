import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { ReportService } from '../../../../services/report.service';
import { Report } from '../../../../models/report';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../../services/auth.service';

@Component({
  selector: 'app-repot-details-page',
  templateUrl: './report-details-page.component.html',
  styleUrl: './report-details-page.component.css'
})
export class ReportDetailsPageComponent {
  report!: Report;
  constructor(private reportService: ReportService, private activatedRoute: ActivatedRoute, public authService: AuthService) {}

  ngOnInit(): void {
    this.initializeReport()
  }

  initializeReport() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.report instanceof HttpErrorResponse)) {
        this.report = response.report;
      } else {
        this.report = {} as Report;
      }
    })
  }

  getImage(fileName: string, index: string) {
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }
}
