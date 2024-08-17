import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Report } from '../../../models/report';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css'
})
export class ProfilePageComponent {
  myReports: Report[] = [];
  constructor(private activatedRoute: ActivatedRoute, public authService: AuthService) {}

  ngOnInit(): void {
    this.initializeReports();
  }

  initializeReports() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.myReports instanceof HttpErrorResponse)) {
        this.myReports = response.myReports;
      } else {
        this.myReports = {} as Report[];
      }
    })
  }
}
