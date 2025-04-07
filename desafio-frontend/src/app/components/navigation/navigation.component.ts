import { LocalService } from './../../services/authenticantion/local-service.service';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { AuthService } from '../../services/authenticantion/auth.service';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent {
  constructor(private auth: AuthService,private router: Router,
     private localStore: LocalService) { }

     private userName:string = ''
     ngOnInit(): void {
     this.getUsername()
    }

    get username(){
      return this.userName
    }
    
  logout(){
    this.auth.logout()
  }

  getUsername(){
    this.auth.user$.subscribe(user => {
      this.userName = user.name;
    });
  }
}