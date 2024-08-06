import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './index-page/index-page.component';
import { UsersListPageComponent } from './users-list-page/users-list-page.component';
import { CategoryPageComponent } from './category-page/category-page.component';
import { AuthGuard } from '../../route-guards/auth.guard';

const routes: Routes = [
  {
    path: "",
    component: IndexPageComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "utilizatori",
    component: UsersListPageComponent
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
