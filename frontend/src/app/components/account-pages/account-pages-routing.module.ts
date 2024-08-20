import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './index-page/index-page.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { ConfirmEmailPageComponent } from './confirm-email-page/confirm-email-page.component';
import { myReportsResolver } from '../../resovers/my-reports.resolver';
import { myAnnouncementsResolver } from '../../resovers/my-announcements.resolver';
import { FavoriteAdsPageComponent } from './favorite-ad-page/favorite-ad-page.component';
import { MessagesSellPageComponent } from './messages-sell-page/messages-sell-page.component';
import { MessagesBuyPageComponent } from './messages-buy-page/messages-buy-page.component';

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
    children: [
      {
        path: "de-vandut",
        component: MessagesSellPageComponent
      },
      {
        path: "de-cumparat",
        component: MessagesBuyPageComponent
      }
    ]
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
