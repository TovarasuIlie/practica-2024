import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category } from '../models/category';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  getAllCategories() {
    return this.http.get<Category[]>(environment.API_URL + "/api/Categories/get-all-categories");
  }

  addCategory(category: Category) {
    const headers = new HttpHeaders().append("Content-Disposition", 'multipart/form-data');
    let categoryData = new FormData();
    console.log(category)
    categoryData.append("category", new Blob([JSON.stringify({ name: category.name })], { type: "application/json" }));
    categoryData.append("image", category.image);
    return this.http.post(environment.API_URL + "/api/Categories/add-category", categoryData, {headers})
  }

  deleteCategory(id: number) {
    return this.http.delete(environment.API_URL + "/api/Categories/delete-category/" + id);
  }
}
