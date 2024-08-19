import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Announcement } from '../../../../models/announcement';
import { AnnouncementService } from '../../../../services/announcement.service';

@Component({
  selector: 'app-search-for-ads-page',
  templateUrl: './search-for-ads-page.component.html',
  styleUrl: './search-for-ads-page.component.css'
})
export class SearchForAdsPageComponent implements OnInit {
  findAds: Announcement[] = [];
  keyword!: string;

  constructor(private adService: AnnouncementService, private activatedRoute: ActivatedRoute) {
    this.keyword = this.activatedRoute.snapshot.queryParams['keyword'];
  }

  ngOnInit(): void {
    this.initializeAds();
  }

  initializeAds() {
    this.activatedRoute.queryParams.subscribe(param => {
      this.adService.getAnnouncementsByTitle(param['keyword']).subscribe(ads => { 
        this.findAds = ads 
        console.log(ads);
      });
    });
    console.log(this.findAds)
  }
}
