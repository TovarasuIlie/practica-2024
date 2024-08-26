import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Announcement } from '../../../../models/announcement';
import { AnnouncementService } from '../../../../services/announcement.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../../../services/auth.service';
import { environment } from '../../../../../environments/environment.development';

@Component({
  selector: 'app-search-for-ads-page',
  templateUrl: './search-for-ads-page.component.html',
  styleUrl: './search-for-ads-page.component.css'
})
export class SearchForAdsPageComponent implements OnInit {
  ads: Announcement[] = [];
  keyword!: string;
  loadingAds: boolean = true;

  constructor(private adService: AnnouncementService, private activatedRoute: ActivatedRoute, public authService: AuthService) {
    this.keyword = this.activatedRoute.snapshot.queryParams['keyword'];
  }

  ngOnInit(): void {
    this.initializeAds();
  }

  initializeAds() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.ad instanceof HttpErrorResponse)) {
        this.ads = response.ads;
      } else {
        this.ads = {} as Announcement[];
      }
      this.loadingAds = false;
    })
  }

  getImage(fileName: string, index: string) {
    return environment.API_URL + "/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }
}
