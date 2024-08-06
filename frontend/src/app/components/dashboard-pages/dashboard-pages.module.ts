import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardPagesRoutingModule } from './dashboard-pages-routing.module';
import { IndexPageComponent } from './index-page/index-page.component';
import { UsersListPageComponent } from './users-list-page/users-list-page.component';
import { CategoryPageComponent } from './category-page/category-page.component';
import { SharedModule } from './shared-components/shared.module';

import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule} from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';


@NgModule({
  declarations: [
    IndexPageComponent,
    UsersListPageComponent,
    CategoryPageComponent
  ],
  imports: [
    CommonModule,
    DashboardPagesRoutingModule,
    SharedModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule
]
})
export class DashboardPagesModule { }
