import { UserFormComponent } from './components/user/user-form/user-form.component';
import { UserListComponent } from './components/user/user-list/user-list.component';
import { AdminGuard } from './components/guard/admin.guard';
import { LoginComponent } from './components/login/login.component';
import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ProductListComponent } from './components/products/product-list/product-list.component';
import { ProductFormComponent } from './components/products/product-form/product-form.component';
import { ProductDetailComponent } from './components/products/product-detail/product-detail.component';
import { CategoryListComponent } from './components/categories/category-list/category-list.component';
import { CategoryFormComponent } from './components/categories/category-form/category-form.component';
import { CategoryDetailComponent } from './components/categories/category-detail/category-detail.component';
import { AuthGuard } from './components/guard/as-token-guard';
import { UserDetailComponent } from './components/user/user-detail/user-detail.component';
import { UserFormContextComponent } from './components/user/user-form-context/user-form-context.component';

export const routes: Routes = [
  { path: '', component: HomeComponent, canActivate:[AuthGuard] },
  {path: 'login', component: LoginComponent},
  { path: 'products', component: ProductListComponent,canActivate:[AuthGuard] },
  { path: 'products/new', component: ProductFormComponent,canActivate:[AdminGuard] },
  { path: 'products/:id', component: ProductDetailComponent },
  { path: 'products/:id/edit', component: ProductFormComponent,canActivate:[AdminGuard]  },
  { path: 'categories', component: CategoryListComponent,canActivate:[AuthGuard] },
  { path: 'categories/new', component: CategoryFormComponent,canActivate:[AdminGuard] },
  { path: 'categories/:id', component: CategoryDetailComponent,canActivate:[AuthGuard] },
  { path: 'categories/:id/edit', component: CategoryFormComponent,canActivate:[AdminGuard] },
  { path: 'users', component: UserListComponent,canActivate:[AdminGuard] },
  { path: 'users/new', component:UserFormComponent,canActivate:[AdminGuard] },
  { path: 'users/context', component:UserFormContextComponent,canActivate:[AuthGuard] },
  { path: 'users/:id', component: UserDetailComponent,canActivate:[AdminGuard] },
  { path: 'users/:id/edit', component:UserFormComponent,canActivate:[AdminGuard] },
  { path: '**', redirectTo: 'login' }
];