import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit, Renderer2 } from '@angular/core';
import { AuthService } from '../../../../services/auth.service';
import { Report } from '../../../../models/report';
import { ReportService } from '../../../../services/report.service';

@Component({
  selector: 'app-repots-list-page',
  templateUrl: './reports-list-page.component.html',
  styleUrl: './reports-list-page.component.css'
})
export class ReportsListPageComponent implements OnInit {
  
  reports: Report[] = [];

  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, public authService: AuthService, private reportService: ReportService) {
    const link = this._renderer2.createElement('link');
    link.href = "/assets/dashboard/css/style.css";
    link.rel = "stylesheet"
    this._renderer2.appendChild(this._document.head, link);
  }

  ngOnInit(): void {
    this.initiliazeReports();
  }

  initiliazeReports() {
    this.reportService.getAllReports().subscribe(reports => this.reports = reports);
  }
  
}
