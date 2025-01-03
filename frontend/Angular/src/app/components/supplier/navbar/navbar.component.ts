import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Component, PLATFORM_ID } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { GetCurrentUserResponse } from '../../../models/response/user/get-current-user-response';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule,RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrls:[ './navabar.component.css',  './demo.css']
})
export class NavbarComponent {
isSidebarOpen = false;
  CurrentUser: GetCurrentUserResponse = new GetCurrentUserResponse();

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  constructor( @Inject(PLATFORM_ID) private platformId: Object, private router: Router){}

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

  }

}
function Inject(PLATFORM_ID: any): (target: typeof NavbarComponent, propertyKey: undefined, parameterIndex: 0) => void {
  throw new Error('Function not implemented.');
}

