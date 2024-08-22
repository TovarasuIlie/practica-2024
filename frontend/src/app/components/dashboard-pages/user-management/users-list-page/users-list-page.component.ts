import { Component, Inject, OnInit, Renderer2 } from '@angular/core';
import { AuthService } from '../../../../services/auth.service';
import { DOCUMENT } from '@angular/common';
import { User } from '../../../../models/user';
import { UserManagementService } from '../../../../services/user-management.service';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-users-list-page',
  templateUrl: './users-list-page.component.html',
  styleUrl: './users-list-page.component.css'
})
export class UsersListPageComponent implements OnInit {
  users: User[] = [];
  length = 50;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];

  hidePageSize = false;
  showPageSizeOptions = true;
  showFirstLastButtons = true;
  disabled = false;

  pageEvent!: PageEvent;
  
  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, public authService: AuthService, private userManageService: UserManagementService) {}
  
  ngOnInit(): void {
    this.initializeUsers();
  }

  initializeUsers() {
    this.userManageService.getAllUsers().subscribe(users => this.users = users)
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
  }
}
