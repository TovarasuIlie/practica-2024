import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Announcement } from '../../../../models/announcement';
import { AnnouncementService } from '../../../../services/announcement.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-search-for-ads-page',
  templateUrl: './search-for-ads-page.component.html',
  styleUrl: './search-for-ads-page.component.css'
})
export class SearchForAdsPageComponent implements OnInit {
  ads: Announcement[] = [];
  keyword!: string;

  constructor(private adService: AnnouncementService, private activatedRoute: ActivatedRoute) {
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
    })
    console.log(this.ads)
  }

  getImage(fileName: string, index: string) {
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }
}
