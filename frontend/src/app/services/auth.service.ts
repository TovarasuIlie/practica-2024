import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { map, of, ReplaySubject } from 'rxjs';
import { User, UserLogin, UserRegister } from '../models/user';
import { Router } from '@angular/router';
import { BrowserStorageService } from './browser-storage.service';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private userSource = new ReplaySubject<User | null>(1);
  user$ = this.userSource.asObservable();

  constructor(private http: HttpClient, private router: Router, private localStorage: BrowserStorageService) { }

  refreshUser(jwt: string | null) {
    if(jwt === null) {
      this.userSource.next(null);
      return of(undefined);
    }

    let headers = new HttpHeaders();
    headers = headers.set("Authorization", "Bearer " + jwt);

    return this.http.get<User>(environment.API_URL + "/api/Authentification/refresh-page", {headers}).pipe(
      map((user: User) => {
        if(user) {
          console.log(user);
          this.setUser(user);
        }
      })
    );
  }

  loginUser(user: UserLogin) {
    return this.http.post<User>(environment.API_URL + "/api/Authentification/login",  user).pipe(
      map((user: User) => {
        if(user) {
          this.setUser(user);
        }
      })
    );
  }

  logOut() {
    this.localStorage.removeItem("auth");
    this.userSource.next(null);
    this.router.navigateByUrl('/');
  }

  registerUser(user: UserRegister) {
    return this.http.post(environment.API_URL + "/api/Authentification/register", user);
  }

  private setUser(user: User) {
    this.localStorage.setItem("auth", JSON.stringify(user));
    this.userSource.next(user);
  }

  getJWT() {
    const key = this.localStorage.getItem("auth");
    if(key) {
      const user: User = JSON.parse(key);
      return user.jwt;
    } else {
      return null;
    }
  }

}
