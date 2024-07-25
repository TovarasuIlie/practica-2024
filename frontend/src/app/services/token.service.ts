import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  set setToken(token: string) {
    sessionStorage.setItem(environment.TOKEN_KEY, token);
  }

  get getToken() {
    return sessionStorage.getItem(environment.TOKEN_KEY) as string;
  }
}
