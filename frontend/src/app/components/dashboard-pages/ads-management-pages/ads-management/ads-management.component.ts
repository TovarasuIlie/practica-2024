import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit, Renderer2 } from '@angular/core';
import { AuthService } from '../../../../services/auth.service';
import { Announcement } from '../../../../models/announcement';
import { AnnouncementManageService } from '../../../../services/announcement-manage.service';

@Component({
  selector: 'app-ads-management',
  templateUrl: './ads-management.component.html',
  styleUrl: './ads-management.component.css'
})
export class AdsManagementComponent implements OnInit {
  ads: Announcement[] = [];

  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, public authService: AuthService, private adsManageService: AnnouncementManageService) {
    const link = this._renderer2.createElement('link');
    link.href = "/assets/dashboard/css/style.css";
    link.rel = "stylesheet"
    this._renderer2.appendChild(this._document.head, link);
  }

  ngOnInit(): void {
    this.initializeAds();
  }

  initializeAds() {
    this.adsManageService.getAllAds().subscribe(ads => this.ads = ads);
  }
}
