import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User, UserAdmin, UserEdit } from '../models/user';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class UserManagementService {

  constructor(private http: HttpClient) { }

  getAllUsers() {
    return this.http.get<User[]>(environment.API_URL + "/api/UserManagement/get-all-users");
  }

  getSpecificUser(id: string) {
    return this.http.get<UserAdmin>(environment.API_URL + "/api/UserManagement/get-user/" + id);
  }

  forceConfirmEmail(id: string) {
    return this.http.get(environment.API_URL + "/api/UserManagement/confirm-email/" + id)
  }

  editUser(id: string, editedUser: UserEdit) {
    return this.http.put(environment.API_URL + "/api/UserManagement/update-user/" + id, editedUser)
  }

  editUserRole(id: string, role: any) {
    return this.http.put(environment.API_URL + "/api/UserManagement/update-role/" + id, role)
  }

  deleteUser(id: string) {
    return this.http.delete(environment.API_URL + "/api/UserManagement/delete-user/" + id);
  }
}
