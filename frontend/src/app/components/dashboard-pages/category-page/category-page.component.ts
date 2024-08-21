import { ChangeDetectorRef, Component, ElementRef, Inject, OnInit, QueryList, Renderer2, ViewChild, ViewChildren } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { DOCUMENT } from '@angular/common';
import { MatTableDataSource } from '@angular/material/table';
import { Category } from '../../../models/category';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatSort, Sort } from '@angular/material/sort';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../../../services/category.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-category-page',
  templateUrl: './category-page.component.html',
  styleUrl: './category-page.component.css'
})
export class CategoryPageComponent implements OnInit {
  loading: boolean = true;
  displayedColumns: string[] = ['id', 'image', 'name', 'actions'];
  dataSource!: MatTableDataSource<Category>;
  categoryForm: FormGroup = new FormGroup({})
  imageArray!: File;
  errorMessages: string[] = [];
  pondOptions = {
    class: 'my-filepond',
    multiple: false,
    labelIdle: '<u>Drag & Drop</u> or <u>Browse</u> images here!',
    acceptedFileTypes: 'image/jpeg, image/png, image/jpg',
  }
  selectedCategory!: number | null;
  @ViewChildren("closeModal") closeModal!: QueryList<ElementRef>;
  @ViewChild(MatPaginator) paginator!: MatPaginator
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, private _liveAnnouncer: LiveAnnouncer, public authService: AuthService,
              private activatedRoute: ActivatedRoute, private fb: FormBuilder, private categoryService: CategoryService, private toastService: ToastService) {
    const link = this._renderer2.createElement('link');
    link.href = "/assets/dashboard/css/style.css";
    link.rel = "stylesheet"
    this._renderer2.appendChild(this._document.head, link);
  }

  ngOnInit(): void {
    this.initializeCategories();
    this.initializeFrom();
  }

  initializeCategories() {
    this.activatedRoute.data.subscribe((response: any) => {
      if(!(response.categories instanceof HttpErrorResponse)) {
        this.dataSource = new MatTableDataSource<Category>(response.categories);
      } else {
        this.dataSource = new MatTableDataSource<Category>([]);
      }
    })
  }

  initializeFrom() {
    this. categoryForm = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(70)]],
      image: [[]]
    })
  }

  refreshCategory() {
    this.categoryService.getAllCategories().subscribe(categories => this.dataSource = new MatTableDataSource<Category>(categories))
  }

  getImage(fileName: string) {
    return "http://localhost:8080/category-imgs/" + fileName;
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  onChange($event: any) {
    this.categoryForm.patchValue({
      image: $event.file.file
    });
  }

  onDelete($event: any) {
    this.categoryForm.patchValue({
      image: this.imageArray
    });
  }

  addCategory() {
    this.errorMessages = []
    if(this.categoryForm.valid) {
      this.categoryService.addCategory(this.categoryForm.value).subscribe({
        next: (value) => {
          this.refreshCategory();
          this.toastService.show({title: "Categorie stearsa", message: "Categoria a fost stearsa cu succes!", classname: "text-success"});
          this.closeModal.forEach(x => x.nativeElement.click());
        },
        error: (response) => {
          console.log(response)
        }
      })
    } else {
      this.errorMessages.push("Toate campurile trebuie completate!")
    }
  }

  selectCategory(id: number) {
    this.selectedCategory = id;
  }

  deleteCategory() {
    if(this.selectedCategory) {
      this.categoryService.deleteCategory(this.selectedCategory).subscribe({
        next: _ => {
          this.refreshCategory();
          this.toastService.show({title: "Categorie stearsa", message: "Categoria a fost stearsa cu succes!", classname: "text-success"});
          this.closeModal.forEach(x => x.nativeElement.click());
          this.selectedCategory = null;
        }
      })
    }
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }
}
