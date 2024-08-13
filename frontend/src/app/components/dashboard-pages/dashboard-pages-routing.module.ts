import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './index-page/index-page.component';
import { UsersListPageComponent } from './user-management/users-list-page/users-list-page.component';
import { CategoryPageComponent } from './category-page/category-page.component';
import { UserDetailsPageComponent } from './user-management/user-details-page/user-details-page.component';
import { userByIdResolver } from '../../resovers/user-by-id.resolver';
import { AdsManagementComponent } from './ads-management-pages/ads-management/ads-management.component';
import { AdDetailsPageComponent } from './ads-management-pages/ad-details-page/ad-details-page.component';
import { announcementManagementResolver } from '../../resovers/announcement-management.resolver';

const routes: Routes = [
  {
    path: "",
    component: IndexPageComponent,
  },
  {
    path: "utilizatori",
    children: [
      {
        path: "",
        component: UsersListPageComponent
      },
      {
        path: "detalii-utilizator/:id",
        component: UserDetailsPageComponent,
        resolve: {
          user: userByIdResolver
        }
      }
    ]
  },
  {
    path: "management-anunturi",
    children: [
      {
        path: "",
        component: AdsManagementComponent
      },
      {
        path: "detalii-anunt/:id",
        component: AdDetailsPageComponent,
        resolve: {
          ad: announcementManagementResolver
        }
      }
    ]
  },
  {
    path: "categorii",
    component: CategoryPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardPagesRoutingModule { }
