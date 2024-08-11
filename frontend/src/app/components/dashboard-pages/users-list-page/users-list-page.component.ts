import { Component, Inject, Renderer2 } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-users-list-page',
  templateUrl: './users-list-page.component.html',
  styleUrl: './users-list-page.component.css'
})
export class UsersListPageComponent {

  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, public authService: AuthService) {
    const link = this._renderer2.createElement('link');
    link.href = "/assets/dashboard/css/style.css";
    link.rel = "stylesheet"
    this._renderer2.appendChild(this._document.head, link);

  }
}
