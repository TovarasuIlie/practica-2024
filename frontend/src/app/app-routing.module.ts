import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './components/main-pages/index-page/index-page.component';
import { AdvertisementPageComponent } from './components/main-pages/announcement-pages/advertisement-page/advertisement-page.component';
import { AddAdvertismentPageComponent } from './components/main-pages/announcement-pages/add-advertisment-page/add-advertisment-page.component';
import { RegisterPageComponent } from './components/main-pages/auth-pages/register-page/register-page.component';
import { AuthGuard } from './route-guards/auth.guard';
import { UnauthGuard } from './route-guards/unauth.guard';
import { AdminGuard } from './route-guards/admin.guard';
import { EditAdvertisementPageComponent } from './components/main-pages/announcement-pages/edit-advertisement-page/edit-advertisement-page.component';
import { editAnnouncementResolver } from './resovers/edit-announcement.resolver';
import { AllAdsFromComponent } from './components/main-pages/announcement-pages/all-ads-from/all-ads-from.component';
import { AdsListByCategoryComponent } from './components/main-pages/announcement-pages/ads-list-by-category/ads-list-by-category.component';
import { SearchForAdsPageComponent } from './components/main-pages/announcement-pages/search-for-ads-page/search-for-ads-page.component';
import { ConfirmEmailPageComponent } from './components/account-pages/confirm-email-page/confirm-email-page.component';
import { searchAdsResolver } from './resovers/search-ads.resolver';
import { ShowMoreAdsComponent } from './components/main-pages/show-more-ads/show-more-ads.component';
import { getAdsFromResolver } from './resovers/get-ads-from.resolver';

const routes: Routes = [
  {
    path: '',
    component: IndexPageComponent
  },
  {
    path: "adauga-anunt",
    canActivate: [AuthGuard],
    children: [
      {
        path: "",
        component: AddAdvertismentPageComponent
      },
      {
        path: "editeaza/:id",
        component: EditAdvertisementPageComponent,
        resolve: {
          ad: editAnnouncementResolver
        }
      }
    ]
  },
  {
    path: 'inregistreaza-te',
    canActivate: [UnauthGuard],
    component: RegisterPageComponent
  },
  {
    path: 'anunt/:adTitle',
    component: AdvertisementPageComponent
  },
  {
    path: "anunturi-din-categoria/:categorySearchLink",
    component: AdsListByCategoryComponent
  },
  {
    path: "anunturi",
    component: ShowMoreAdsComponent
  },
  {
    path: "cauta-anunturi",
    component: SearchForAdsPageComponent,
    resolve: {
      ads: searchAdsResolver
    }
  },
  {
    path: "oferte/:username",
    component: AllAdsFromComponent,
    resolve: {
      ads: getAdsFromResolver
    }
  },
  {
    path: 'contul-meu',
    canActivate: [AuthGuard],
    loadChildren: () => import('./components/account-pages/account-pages.module').then(module => module.AccountPagesModule),
  },
  {
    path: "reseteaza-parola",
    canActivate: [UnauthGuard],
    loadChildren: () => import("./components/main-pages/auth-pages/auth-pages.module").then(module => module.AuthPagesModule),
  },
  {
    path: 'contul-meu/confirma-email',
    component: ConfirmEmailPageComponent,
    canActivate: [UnauthGuard],
  },
  {
    path: "dashboard",
    canActivate: [AuthGuard, AdminGuard],
    loadChildren: () => import("./components/dashboard-pages/dashboard-pages.module").then(module => module.DashboardPagesModule)
  },
  {
    path: '**',
    redirectTo: "eroarea-404",
    pathMatch: "full",
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
