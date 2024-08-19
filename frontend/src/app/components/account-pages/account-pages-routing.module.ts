import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './index-page/index-page.component';
import { MessagesPageComponent } from './messages-page/messages-page.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { ConfirmEmailPageComponent } from './confirm-email-page/confirm-email-page.component';
import { myReportsResolver } from '../../resovers/my-reports.resolver';
import { myAnnouncementsResolver } from '../../resovers/my-announcements.resolver';
import { FavoriteAdsPageComponent } from './favorite-ad-page/favorite-ad-page.component';

const routes: Routes = [
  {
    path: "",
    component: IndexPageComponent,
    resolve: {
      myAds: myAnnouncementsResolver
    }
  },
  {
    path: "mesaje",
    component: MessagesPageComponent
  },
  {
    path: "profil",
    component: ProfilePageComponent,
    resolve: {
      myReports: myReportsResolver
    }
  },
  {
    path: "favorite",
    component: FavoriteAdsPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountPagesRoutingModule { }
