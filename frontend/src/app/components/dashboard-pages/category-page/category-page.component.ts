import { ChangeDetectorRef, Component, Inject, OnInit, Renderer2, ViewChild } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { DOCUMENT } from '@angular/common';
import { MatTableDataSource } from '@angular/material/table';
import { Category } from '../../../models/category';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatSort, Sort } from '@angular/material/sort';

@Component({
  selector: 'app-category-page',
  templateUrl: './category-page.component.html',
  styleUrl: './category-page.component.css'
})
export class CategoryPageComponent implements OnInit {
  loading: boolean = true;
  displayedColumns: string[] = ['id', 'name', 'actions'];
  dataSource = new MatTableDataSource<Category>(ELEMENT_DATA);

  @ViewChild(MatPaginator) paginator!: MatPaginator
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private _renderer2: Renderer2, @Inject(DOCUMENT) private _document: Document, private _liveAnnouncer: LiveAnnouncer, public authService: AuthService) {
    const link = this._renderer2.createElement('link');
    link.href = "/assets/dashboard/css/style.css";
    link.rel = "stylesheet"
    this._renderer2.appendChild(this._document.head, link);
  }

  ngOnInit(): void {
    
  }

  ngAfterViewInit() {   
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

    /** Announce the change in sort state for assistive technology. */
    announceSortChange(sortState: Sort) {
      // This example uses English messages. If your application supports
      // multiple language, you would internationalize these strings.
      // Furthermore, you can customize the message to add additional
      // details about the values being sorted.
      if (sortState.direction) {
        this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
      } else {
        this._liveAnnouncer.announce('Sorting cleared');
      }
    }
}

const ELEMENT_DATA: Category[] = [
    {id: 1, name: "Masini"},
    {id: 2, name: "Masini"},
    {id: 3, name: "Masini"},
    {id: 4, name: "Masini"},
    {id: 5, name: "Masini"},
    {id: 6, name: "Masini"},
    {id: 7, name: "Masini"},
    {id: 8, name: "Masini"},
    {id: 9, name: "Masini"},
    {id: 10, name: "Masini"},
    {id: 11, name: "Masini"},
    {id: 12, name: "Masini"},
    {id: 13, name: "Masini"},
    {id: 14, name: "Masini"},
    {id: 15, name: "Masini"},
    {id: 16, name: "Masini"}
]
