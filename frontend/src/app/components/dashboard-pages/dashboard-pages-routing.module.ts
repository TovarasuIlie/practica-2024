import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './index-page/index-page.component';
import { UsersListPageComponent } from './user-management/users-list-page/users-list-page.component';
import { CategoryPageComponent } from './category-page/category-page.component';
import { AuthGuard } from '../../route-guards/auth.guard';
import { UserDetailsPageComponent } from './user-management/user-details-page/user-details-page.component';
import { userByIdResolver } from '../../resovers/user-by-id.resolver';

const routes: Routes = [
  {
    path: "",
    component: IndexPageComponent,
    canActivate: [AuthGuard]
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
    path: "categorii",
    component: CategoryPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardPagesRoutingModule { }
