import { PageResponse } from './../models/page.model';
import { environment } from './../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getAllProducts(page:number = 0,size:number = 10): Observable<PageResponse<Product>> {
    return this.http.get<PageResponse<Product>>(`${this.apiUrl}/v2/products?page=${page}&size=${size}`);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/v2/products/${id}`);
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.apiUrl}/v2/products`, product);
  }

  updateProduct(id: number, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiUrl}/v2/products/${id}`, product);
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/v2/products/${id}`);
  }
}