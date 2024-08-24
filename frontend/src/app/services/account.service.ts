import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { EditProfile, User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient) { }

  sendCode(email: string) {
    return this.http.get(environment.API_URL + "/api/Account/forgot-password/" + email);
  }

  resetPassword(code: string, password: string) {
    const requestBody: any = {
      code: code,
      password: password
    }
    return this.http.post(environment.API_URL + "/api/Account/reset-password", requestBody);
  }

  getAccount() {
    return this.http.get<User>(environment.API_URL + "/api/Account/get-account");
  }

  updateProfile(user: EditProfile) {
    return this.http.put<User>(environment.API_URL + "/api/Account/update-profile", user);
  }
}
