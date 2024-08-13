import { Component, Inject, OnInit, Renderer2 } from '@angular/core';
import { AuthService } from '../../../../services/auth.service';
import { DOCUMENT } from '@angular/common';
import { User } from '../../../../models/user';
import { UserManagementService } from '../../../../services/user-management.service';

@Component({
  selector: 'app-users-list-page',
  templateUrl: './users-list-page.component.html',
  styleUrl: './users-list-page.component.css'
})
export class UsersListPageComponent implements OnInit {
  users: User[] = [];
  
  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, public authService: AuthService, private userManageService: UserManagementService) {
    const link = this._renderer2.createElement('link');
    link.href = "/assets/dashboard/css/style.css";
    link.rel = "stylesheet"
    this._renderer2.appendChild(this._document.head, link);
  }
  
  ngOnInit(): void {
    this.initializeUsers();
  }

  initializeUsers() {
    this.userManageService.getAllUsers().subscribe(users => this.users = users)
  }
}
