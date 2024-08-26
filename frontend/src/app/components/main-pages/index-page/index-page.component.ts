import { Component, OnInit } from '@angular/core';
import { Announcement } from '../../../models/announcement';
import { AnnouncementService } from '../../../services/announcement.service';
import { CategoryService } from '../../../services/category.service';
import { Category } from '../../../models/category';
import { AuthService } from '../../../services/auth.service';
import { environment } from '../../../../environments/environment.development';

@Component({
  selector: 'app-index-page',
  templateUrl: './index-page.component.html',
  styleUrl: './index-page.component.css'
})
export class IndexPageComponent implements OnInit {
  ads: Announcement[] = [];
  categories: Category[] = [];
  loadingAds: boolean = true;
  loadingCategory: boolean = true;

  constructor(private adService: AnnouncementService, private categoryService: CategoryService, public authService: AuthService) {}
  
  ngOnInit(): void {
    this.initializeAds();
    this.initializeCategories();
  }

  initializeAds() {
    this.adService.getAnnouncements().subscribe(ads => {
      this.ads = ads;
      this.loadingAds = false;
    })
  }

  initializeCategories() {
    this.categoryService.getAllCategories().subscribe(categories => {
        this.categories = categories;
        this.loadingCategory = false;
      }
    )
  }

  getImage(fileName: string, index: string) {
    return environment.API_URL + "/ads-imgs/" + fileName + "/" + fileName + "-" + index + ".jpeg";
  }

  getImageCategory(fileName: string) {
    return environment.API_URL + "/category-imgs/" + fileName;
  }
}
