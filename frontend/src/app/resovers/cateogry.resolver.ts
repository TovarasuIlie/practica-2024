import { ResolveFn } from '@angular/router';
import { Category } from '../models/category';
import { inject } from '@angular/core';
import { CategoryService } from '../services/category.service';
import { catchError, of } from 'rxjs';

export const cateogryResolver: ResolveFn<Category[]> = (route, state) => {
  const categoryService = inject(CategoryService);

  return categoryService.getAllCategories().pipe(
    catchError((response) => {
      return of(response)
    })
  )
};
