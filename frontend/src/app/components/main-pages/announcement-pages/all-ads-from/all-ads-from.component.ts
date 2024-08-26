import { Component, OnInit } from '@angular/core';
import { Announcement } from '../../../../models/announcement';
import { AnnouncementService } from '../../../../services/announcement.service';
import { ActivatedRoute } from '@angular/router';
import { environment } from '../../../../../environments/environment.development';
import { User } from '../../../../models/user';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-all-ads-from',
  templateUrl: './all-ads-from.component.html',
  styleUrl: './all-ads-from.component.css'
})
export class AllAdsFromComponent implements OnInit {
  ads: Announcement[] = [];
  loadingAds: boolean = true;
  user!: User;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.initializeAds();
  }

  initializeAds() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.ads instanceof HttpErrorResponse)) {
        this.ads = response.ads;
        this.user = response.ads[0].user;
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
