import { Component, Inject, OnInit, Renderer2 } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { DOCUMENT } from '@angular/common';
import { DashboardDetails } from '../../../models/dashboard-details';
import { DashboardDetailsService } from '../../../services/dashboard-details.service';
import { ToastService } from '../../../services/toast.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-index-page',
  templateUrl: './index-page.component.html',
  styleUrl: './index-page.component.css'
})
export class IndexPageComponent {
  dashboardDetails!: DashboardDetails;

  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, public authService: AuthService, private dashboardService: DashboardDetailsService, private router: Router, private toastService: ToastService) {
    this.dashboardService.getAllDetails().subscribe(
      details => this.dashboardDetails = details
    )
  }

  logout() {
    this.authService.logOut();
    this.router.navigateByUrl("/");
    this.toastService.show({title: "Iesire din cont!", message: "Te-ai delogat cu succes!", classname: "text-success"});
  }
}
