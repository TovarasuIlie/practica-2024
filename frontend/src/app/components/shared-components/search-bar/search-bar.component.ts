import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})
export class SearchBarComponent implements OnInit {
  selected!: string;

  searchForm: FormGroup = new FormGroup({})
  constructor(private router: Router, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initilizeForm();
  }

  initilizeForm() {
    this.searchForm = this.fb.group({
      keyword: [null, [Validators.required]]
    })
  }

  searchFor() {
    if(this.searchForm.valid) {
      this.router.navigate(['/cauta-anunturi'], { queryParams: { keyword: this.searchForm.value.keyword }})
    }
  }
}
