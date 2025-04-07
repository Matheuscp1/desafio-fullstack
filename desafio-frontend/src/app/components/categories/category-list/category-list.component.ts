import { AuthService } from './../../../services/authenticantion/auth.service';
import { PaginationComponent } from './../../pagination/pagination.component';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { Category } from '../../../models/category.model';
import { CategoryService } from '../../../services/category.service';

@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatTooltipModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    PaginationComponent,
  ],
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.scss'],
})
export class CategoryListComponent implements OnInit {
  categories: Category[] = [];
  displayedColumns: string[] = ['id', 'name', 'description', 'actions'];
  loading = true;
  pages = 0;
  size = 5;
  currentPage = 0;
  totalPages = 10;
  public role: string = '';

  constructor(
    private categoryService: CategoryService,
    private snackBar: MatSnackBar,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.loadCategories();
    this.getRole();
  }

  getRole() {
    this.auth.user$.subscribe((user) => {
      this.role = user.role;
    });
  }

  loadCategories(): void {
    this.loading = true;
    this.categoryService
      .getAllCategories(this.currentPage, this.size)
      .subscribe({
        next: (data) => {
          this.pages = data.totalPages;
          this.categories = data.content;
          this.loading = false;
        },
        error: (error) => {
          console.error('Error fetching categories', error);
          this.snackBar.open('Error loading categories', 'Close', {
            duration: 3000,
          });
          this.loading = false;
        },
      });
  }

  deleteCategory(id: number): void {
    if (confirm('Are you sure you want to delete this category?')) {
      this.categoryService.deleteCategory(id).subscribe({
        next: () => {
          this.snackBar.open('Category deleted successfully', 'Close', {
            duration: 3000,
          });
          this.loadCategories();
        },
        error: (error) => {
          console.error('Error deleting category', error);
          this.snackBar.open('Error deleting category', 'Close', {
            duration: 3000,
          });
        },
      });
    }
  }

  onPageChange(page: any) {
    this.currentPage = page.page;
    this.size = page.size;
    this.loadCategories();
  }
}
