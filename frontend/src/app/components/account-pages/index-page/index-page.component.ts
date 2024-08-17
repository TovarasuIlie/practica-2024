import { Component } from '@angular/core';
import { Announcement } from '../../../models/announcement';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-index-page',
  templateUrl: './index-page.component.html',
  styleUrl: './index-page.component.css'
})
export class IndexPageComponent {
  myAds: Announcement[] = [];
  activeAds: Announcement[] = [];
  waitingAds: Announcement[] = [];
  disabledAds: Announcement[] = [];
  
  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.initializeAds();
  }

  initializeAds() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.myAds instanceof HttpErrorResponse)) {
        this.activeAds = response.myAds.filter((x: Announcement) =>  x.approved && !x.isDeactivated);
        this.waitingAds = response.myAds.filter((x: Announcement) => !x.approved && !x.isDeactivated);
        this.disabledAds = response.myAds.filter((x: Announcement) => !x.approved && x.isDeactivated);
      } else {
        this.activeAds = {} as Announcement[];
        this.waitingAds = {} as Announcement[];
        this.disabledAds = {} as Announcement[];
      }
    })
  }

  getImage(fileName: string, index: string) {
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }
}
