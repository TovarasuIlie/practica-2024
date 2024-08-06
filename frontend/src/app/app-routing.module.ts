import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './components/main-pages/index-page/index-page.component';
import { AdvertisementPageComponent } from './components/main-pages/advertisement-page/advertisement-page.component';
import { AddAdvertismentPageComponent } from './components/main-pages/add-advertisment-page/add-advertisment-page.component';
import { RegisterPageComponent } from './components/main-pages/auth-pages/register-page/register-page.component';
import { AuthGuard } from './route-guards/auth.guard';
import { UnauthGuard } from './route-guards/unauth.guard';
import { annoucementResolver } from './resovers/annoucement.resolver';

const routes: Routes = [
  {
    path: '',
    component: IndexPageComponent
  },
  {
    path: "adauga-anunt",
    canActivate: [AuthGuard],
    component: AddAdvertismentPageComponent
  },
  {
    path: 'inregistreaza-te',
    component: RegisterPageComponent
  },
  {
    path: 'anunt/:adTitle',
    component: AdvertisementPageComponent,
    resolve: {ad: annoucementResolver}
  },
  {
    path: 'contul-meu',
    loadChildren: () => import('./components/account-pages/account-pages.module').then(module => module.AccountPagesModule),
  },
  {
    path: "reseteaza-parola",
    loadChildren: () => import("./components/main-pages/auth-pages/auth-pages.module").then(module => module.AuthPagesModule),
  },
  {
    path: "dashboard",
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
