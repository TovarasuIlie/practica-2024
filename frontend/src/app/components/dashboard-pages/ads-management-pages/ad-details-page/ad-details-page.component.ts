import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit, Renderer2 } from '@angular/core';
import { AuthService } from '../../../../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../../services/toast.service';
import { FormBuilder } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Announcement } from '../../../../models/announcement';

@Component({
  selector: 'app-ad-details-page',
  templateUrl: './ad-details-page.component.html',
  styleUrl: './ad-details-page.component.css'
})
export class AdDetailsPageComponent implements OnInit {
  ad: Announcement = <Announcement>{}

  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, public authService: AuthService, 
              private activatedRoute: ActivatedRoute, private toastService: ToastService, private router: Router, private fb: FormBuilder) {
    const link = this._renderer2.createElement('link');
    link.href = "/assets/dashboard/css/style.css";
    link.rel = "stylesheet"
    this._renderer2.appendChild(this._document.head, link);
  }

  ngOnInit(): void {
    this.initializeAd()
    console.log(this.ad)
  }

  initializeAd() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.ad instanceof HttpErrorResponse)) {
        this.ad = response.ad;
      } else {
        this.ad = {} as Announcement;
      }
    })
  }

  getImage(fileName: string, index: string) {
    return "http://localhost:8080/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }
}
