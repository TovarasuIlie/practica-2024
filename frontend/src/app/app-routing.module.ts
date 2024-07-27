import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './components/main-pages/index-page/index-page.component';
import { AdvertisementPageComponent } from './components/main-pages/advertisement-page/advertisement-page.component';
import { AddAdvertismentPageComponent } from './components/main-pages/add-advertisment-page/add-advertisment-page.component';
import { RegisterPageComponent } from './components/main-pages/auth-pages/register-page/register-page.component';
import { ForgotPasswordPageComponent } from './components/main-pages/auth-pages/forgot-password-page/index-page/forgot-password-page.component';
import { AuthGuard } from './route-guards/auth.guard';
import { UnauthGuard } from './route-guards/unauth.guard';

const routes: Routes = [
  {
    path: '',
    component: IndexPageComponent
  },
  {
    path: '',
    redirectTo: '',
    pathMatch: 'full'
  },
  {
    path: "reseteaza-parola",
    loadChildren: () => import("./components/main-pages/auth-pages/auth-pages.module").then(module => module.AuthPagesModule),
    // canActivate: [UnauthGuard]
  },
  {
    path: 'inregistreaza-te',
    component: RegisterPageComponent
  },
  {
    path: 'anunt/:adTitle',
    component: AdvertisementPageComponent
  },
  {
    path: 'adauga-anunt',
    component: AddAdvertismentPageComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'contul-meu',
    loadChildren: () => import('./components/account-pages/account-pages.module').then(module => module.AccountPagesModule),
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
