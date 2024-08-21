import { Component, Inject, OnInit, Renderer2 } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { DOCUMENT } from '@angular/common';
import { DashboardDetails } from '../../../models/dashboard-details';
import { DashboardDetailsService } from '../../../services/dashboard-details.service';

@Component({
  selector: 'app-index-page',
  templateUrl: './index-page.component.html',
  styleUrl: './index-page.component.css'
})
export class IndexPageComponent implements OnInit {
  dashboardDetails!: DashboardDetails;

  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, public authService: AuthService, private dashboardService: DashboardDetailsService) {
    const link = this._renderer2.createElement('link');
    link.href = "/assets/dashboard/css/style.css";
    link.rel = "stylesheet"
    this._renderer2.appendChild(this._document.head, link);
  }
  ngOnInit(): void {
    this.dashboardService.getAllDetails().subscribe(
      details => this.dashboardDetails = details
    )
  }
}
