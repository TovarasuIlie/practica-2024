import { Component } from '@angular/core';

@Component({
  selector: 'app-add-advertisment-page',
  templateUrl: './add-advertisment-page.component.html',
  styleUrl: './add-advertisment-page.component.css'
})
export class AddAdvertismentPageComponent {
  foods: string[] = ['Steak', 'Pizza', 'Tacos'];
}
