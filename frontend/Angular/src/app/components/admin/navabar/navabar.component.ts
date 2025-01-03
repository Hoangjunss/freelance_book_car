import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Component, Inject, NgModule, OnInit, PLATFORM_ID } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { GetCurrentUserResponse } from '../../../models/response/user/get-current-user-response';

@Component({
  selector: 'app-navabar',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule,RouterLinkActive],
  templateUrl: './navabar.component.html',
  styleUrls:[ './navabar.component.css',  './demo.css']
})
export class NavabarComponent implements OnInit{
  isSidebarOpen = false;
  CurrentUser: GetCurrentUserResponse = new GetCurrentUserResponse();

  isAdmin = false;

  currentUrl: string = '';

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  constructor( @Inject(PLATFORM_ID) private platformId: Object, private router: Router){}
  ngOnInit(): void {
    this.currentUrl = this.router.url;
    console.log('Current URL:', this.currentUrl);
    if(this.currentUrl == '/admin/admin'){
      alert("1");
      this.isAdmin = true;
    }else{
      this.isAdmin = false;
    }
  }

  localstore(){
    if (isPlatformBrowser(this.platformId)) {
      const user = localStorage.getItem('currentUser');
      if (user) {
        this.CurrentUser = JSON.parse(user).name;
      } else { 
        this.CurrentUser = null; 
      }
    }
  }

  logout(event?: MouseEvent) {
    if (event) {
      event.preventDefault();
    }
    localStorage.removeItem('currentUser');
    localStorage.removeItem('idUser');
    localStorage.removeItem('idBooking');
    localStorage.removeItem('authToken');
    localStorage.removeItem('token');
    this.CurrentUser = null; 

    this.router.navigate(['/auth/login']);
  }

}
