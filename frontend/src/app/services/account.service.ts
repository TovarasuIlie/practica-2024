import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';

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
}
