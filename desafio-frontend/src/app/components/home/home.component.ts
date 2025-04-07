import { AuthService } from './../../services/authenticantion/auth.service';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  
  constructor(private auth: AuthService) { }
    public role:string = ''
     ngOnInit(): void {
     this.getRole()
    }

  getRole(){
    this.auth.user$.subscribe(user => {
      this.role = user.role;
    });
  }
}