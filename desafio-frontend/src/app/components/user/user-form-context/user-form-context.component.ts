import { CustomSelectComponent } from '../../custom-select/custom-select.component';
import { User } from '../../../models/user.model';
import { UserService } from '../../../services/user.service ';
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
  templateUrl: './user-form-context.component.html',
  styleUrls: ['./user-form-context.component.scss'],
})
export class UserFormContextComponent implements OnInit {
  userForm!: FormGroup;
  userId:any;
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
    this.loaduser()
  }

  initForm(): void {
    this.userForm = this.fb.group({
      name: [''],
      email: [''],
      password: ['', [Validators.required]],
      role: ['USER'],
    });
  }

  loaduser(): void {
    this.loading = true;
    this.userService.getContext().subscribe({
      next: (user) => {

        this.userId = user.id
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

    if (this.userId) {
      this.userService.updatePassowrd(this.userId, userData).subscribe({
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
    }
  }
}
