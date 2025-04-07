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

import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product.service';

@Component({
  selector: 'app-product-list',
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
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  displayedColumns: string[] = [
    'id',
    'name',
    'price',
    'status',
    'code',
    'actions',
  ];
  loading = true;
  pages = 0;
  size = 5;
  currentPage = 0;
  totalPages = 10;
  public role: string = '';

  constructor(
    private productService: ProductService,
    private snackBar: MatSnackBar,
    private auth: AuthService
  ) {}

  getRole() {
    this.auth.user$.subscribe((user) => {
      this.role = user.role;
    });
  }

  ngOnInit(): void {
    this.loadProducts();
    this.getRole();
  }

  loadProducts(): void {
    this.loading = true;
    this.productService.getAllProducts(this.currentPage, this.size).subscribe({
      next: (data) => {
        this.pages = data.totalPages;
        this.products = data.content;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error fetching products', error);
        this.snackBar.open('Error loading products', 'Close', {
          duration: 3000,
        });
        this.loading = false;
      },
    });
  }

  deleteProduct(id: number): void {
    if (confirm('Are you sure you want to delete this product?')) {
      this.productService.deleteProduct(id).subscribe({
        next: () => {
          this.snackBar.open('Product deleted successfully', 'Close', {
            duration: 3000,
          });
          this.loadProducts();
        },
        error: (error) => {
          console.error('Error deleting product', error);
          this.snackBar.open('Error deleting product', 'Close', {
            duration: 3000,
          });
        },
      });
    }
  }

  onPageChange(page: any) {
    this.currentPage = page.page;
    this.size = page.size;
    this.loadProducts();
  }
}
