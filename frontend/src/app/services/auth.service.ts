import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { map, of, ReplaySubject } from 'rxjs';
import { ConfirmEmail, User, UserLogin, UserRegister } from '../models/user';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private userSource = new ReplaySubject<User | null>(1);
  user$ = this.userSource.asObservable();

  constructor(private http: HttpClient, private router: Router) { }

  refreshUser(jwt: string | null) {
    if(jwt === null) {
      this.userSource.next(null);
      return of(undefined);
    }

    let headers = new HttpHeaders();
    headers = headers.append("Authorization", "Bearer " + jwt);

    return this.http.get<User>(environment.API_URL + "/api/Authentification/refresh-page", {headers}).pipe(
      map((user: User) => {
        if(user) {
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

  confirmEmail(confirmEmail: ConfirmEmail) {
    return this.http.put<ConfirmEmail>(environment.API_URL + "/api/Account/confirm-email", confirmEmail);
  }

  logOut() {
    localStorage.removeItem(environment.USER_KEY);
    this.userSource.next(null);
    this.router.navigateByUrl('/');
  }

  registerUser(user: UserRegister) {
    user.address = user.country + ", " + user.address;
    return this.http.post(environment.API_URL + "/api/Authentification/register", user);
  }

  private setUser(user: User) {
    localStorage.setItem(environment.USER_KEY, JSON.stringify(user));
    this.userSource.next(user);
  }

  getJWT() {
    const key = localStorage.getItem(environment.USER_KEY);
    if(key) {
      const user: User = JSON.parse(key);
      return user.jwt;
    } else {
      return null;
    }
  }

  getID() {
    const key = localStorage.getItem(environment.USER_KEY);
    if(key) {
      const user: User = JSON.parse(key);
      const decodedToken: any = jwtDecode(user.jwt);
      return decodedToken.id;
    } else {
      return null;
    }
  }

}
