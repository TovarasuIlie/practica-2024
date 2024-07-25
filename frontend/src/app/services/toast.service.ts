import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  toasts: any[] = [];

  show(option: any = {}) {
      this.toasts.push({...option});
  }

  remove(toast: any) {
    this.toasts = this.toasts.filter(t => t !== toast);
  }
}