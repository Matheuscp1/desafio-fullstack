import { CustomSelectComponent } from './../../custom-select/custom-select.component';
import { User } from './../../../models/user.model';
import { UserService } from '../../../services/user.service ';
import { CustomTextAreaComponent } from '../../custom-textarea/custom-textarea.component';
import { CustomInputComponent } from '../../custom-input/custom-input.component';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    CustomInputComponent,
    CustomSelectComponent,
  ],
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss'],
})
export class UserFormComponent implements OnInit {
  userForm!: FormGroup;
  userId: number | null = null;
  isEditMode = false;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.initForm();

    this.route.params.subscribe((params) => {
      if (params['id'] && params['id'] !== 'new') {
        this.userId = +params['id'];
        this.isEditMode = true;
        this.loaduser(this.userId);
        this.userForm.get('password')?.clearValidators();
        this.userForm.get('password')?.updateValueAndValidity();
      }
    });
  }

  initForm(): void {
    this.userForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      role: ['user', [Validators.required]],
    });
  }

  loaduser(id: number): void {
    this.loading = true;
    this.userService.getUserById(id).subscribe({
      next: (user) => {
        this.userForm.patchValue({
          name: user.name,
          email: user.email,
          password: user.password,
          role: user.role,
          id: user.id || '',
        });
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading user', error);
        this.snackBar.open('Error loading user', 'Close', { duration: 3000 });
        this.loading = false;
        this.router.navigate(['/users']);
      },
    });
  }

  onSubmit(): void {
    console.log(this.userForm.invalid);
    if (this.userForm.invalid) {
      return;
    }

    this.loading = true;
    const userData: User = {
      ...this.userForm.value,
    };

    if (this.isEditMode && this.userId) {
      delete userData.password;
      this.userService.updateUser(this.userId, userData).subscribe({
        next: (_) => {
          this.snackBar.open('user updated successfully', 'Close', {
            duration: 3000,
          });
          this.loading = false;
          this.router.navigate(['/users']);
        },
        error: (error) => {
          console.error('Error updating user', error);
          this.snackBar.open('Error updating user', 'Close', {
            duration: 3000,
          });
          this.loading = false;
        },
      });
    } else {
      this.userService.createtUser(userData).subscribe({
        next: (_) => {
          this.snackBar.open('user created successfully', 'Close', {
            duration: 3000,
          });
          this.loading = false;
          this.router.navigate(['/users']);
        },
        error: (error) => {
          console.error('Error creating user', error);
          this.snackBar.open('Error creating user', 'Close', {
            duration: 3000,
          });
          this.loading = false;
        },
      });
    }
  }
}
