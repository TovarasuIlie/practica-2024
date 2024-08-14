import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SuspendAccount } from '../models/suspend';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class SuspendAccountsService {

  constructor(private http: HttpClient) { }

  getAllSuspendAccounts() {
    return this.http.get<SuspendAccount[]>(environment.API_URL + "/api/Suspended-accounts/all-suspended-accounts");
  }

  suspendAccount(accountID: string, reason: string, numberOfDays: number) {
    return this.http.get(environment.API_URL + "/api/Suspended-accounts/suspend-account/" + accountID + "?numberOfDaysSuspended=" + numberOfDays + "&reason=" + reason);
  }

  unsuspendAccount(accountID: string) {
    return this.http.get(environment.API_URL + "/api/Suspended-accounts/unsuspend-account/" + accountID);
  }
}
