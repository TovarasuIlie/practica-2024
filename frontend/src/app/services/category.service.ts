import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category, CategoryEdit } from '../models/category';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  getAllCategories() {
    return this.http.get<Category[]>(environment.API_URL + "/api/Categories/get-all-categories");
  }

  getCategory(id: number) {
    return this.http.get<Category>(environment.API_URL + "/api/Categories/get-category-by-id/" + id);
  }

  addCategory(category: Category) {
    const headers = new HttpHeaders().append("Content-Disposition", 'multipart/form-data');
    let categoryData = new FormData();
    categoryData.append("category", new Blob([JSON.stringify({ name: category.name })], { type: "application/json" }));
    categoryData.append("image", category.image);
    return this.http.post(environment.API_URL + "/api/Categories/add-category", categoryData, {headers})
  }

  deleteCategory(id: number) {
    return this.http.delete(environment.API_URL + "/api/Categories/delete-category/" + id);
  }

  editCategory(category: CategoryEdit) {
    const headers = new HttpHeaders().append("Content-Disposition", 'multipart/form-data');
    let categoryData = new FormData();
    categoryData.append("category", new Blob([JSON.stringify({ name: category.nameEdit })], { type: "application/json" }));
    categoryData.append("image", category.image);
    return this.http.post(environment.API_URL + "/api/Categories/edit-category/" + category.id, categoryData, {headers})
  }
}
