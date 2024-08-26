import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit, Renderer2 } from '@angular/core';
import { AuthService } from '../../../../services/auth.service';
import { Report } from '../../../../models/report';
import { ReportService } from '../../../../services/report.service';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { ToastService } from '../../../../services/toast.service';

@Component({
  selector: 'app-repots-list-page',
  templateUrl: './reports-list-page.component.html',
  styleUrl: './reports-list-page.component.css'
})
export class ReportsListPageComponent implements OnInit {
  reports: Report[] = [];
  length = 50;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];

  hidePageSize = false;
  showPageSizeOptions = true;
  showFirstLastButtons = true;
  disabled = false;

  pageEvent!: PageEvent;

  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, public authService: AuthService, private reportService: ReportService, private router: Router,
              private toastService: ToastService
) {}

  ngOnInit(): void {
    this.initiliazeReports();
  }

  initiliazeReports() {
    this.reportService.getAllReports().subscribe(reports => this.reports = reports);
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
  }

  logout() {
    this.authService.logOut();
    this.router.navigateByUrl("/");
    this.toastService.show({title: "Iesire din cont!", message: "Te-ai delogat cu succes!", classname: "text-success"});
  }
}
