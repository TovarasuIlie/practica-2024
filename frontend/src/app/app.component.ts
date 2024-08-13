import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.refreshUser();
  }

  private refreshUser() {
    const jwt = this.authService.getJWT();
    if(jwt) {
      this.authService.refreshUser(jwt).subscribe({
        next: _ => {},
        error: _ => {
          this.authService.logOut();
        }
      });
    } else {
      this.authService.refreshUser(null).subscribe();
    }
  }

}
