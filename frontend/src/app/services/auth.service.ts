import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserLogin, UserRegister } from '../models/user';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  loginUser(user: UserLogin) {
    return this.http.post(environment.API_URL + "/api/Authentification/login",  user);
  }

  registerUser(user: UserRegister) {
    return this.http.post(environment.API_URL + "/api/Authentification/register", user);
  }

}
