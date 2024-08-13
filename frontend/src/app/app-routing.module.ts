import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './components/main-pages/index-page/index-page.component';
import { AdvertisementPageComponent } from './components/main-pages/announcement-pages/advertisement-page/advertisement-page.component';
import { AddAdvertismentPageComponent } from './components/main-pages/announcement-pages/add-advertisment-page/add-advertisment-page.component';
import { RegisterPageComponent } from './components/main-pages/auth-pages/register-page/register-page.component';
import { AuthGuard } from './route-guards/auth.guard';
import { UnauthGuard } from './route-guards/unauth.guard';
import { annoucementResolver } from './resovers/annoucement.resolver';
import { AdminGuard } from './route-guards/admin.guard';
import { EditAdvertisementPageComponent } from './components/main-pages/announcement-pages/edit-advertisement-page/edit-advertisement-page.component';
import { editAnnouncementResolver } from './resovers/edit-announcement.resolver';
import { AllAdsFromComponent } from './components/main-pages/announcement-pages/all-ads-from/all-ads-from.component';

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
    component: AdvertisementPageComponent,
    resolve: {ad: annoucementResolver}
  },
  {
    path: "oferte/:username",
    component: AllAdsFromComponent
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
    path: "dashboard",
    canActivate: [AuthGuard, AdminGuard],
    loadChildren: () => import("./components/dashboard-pages/dashboard-pages.module").then(module => module.DashboardPagesModule)
  },
  {
    path: '**',
    redirectTo: "eroarea-404",
    pathMatch: "full",
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
