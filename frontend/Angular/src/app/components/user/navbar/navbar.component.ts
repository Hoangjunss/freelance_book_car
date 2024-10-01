import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../../services/user/user.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  CurrentUser: string | null = null;
  mobileNavbarContent: string = '';
  socialLinks: { icon: string, url: string }[] = [
    { icon: 'fa-brands fa-facebook', url: '#' },
    { icon: 'fa-brands fa-x', url: '#' },
    { icon: 'fa-brands fa-youtube', url: '#' },
    { icon: 'fa-brands fa-tiktok', url: '#' },
    { icon: 'fa-brands fa-instagram', url: '#' },
  ];
  
  navbarContent: { label: string, routerLink?: string, action?: () => void, isUser?: boolean }[] = [];
  dataLoaded: boolean = false;

  constructor(private userService: UserService, private router: Router, @Inject(PLATFORM_ID) private platformId: Object) { }

  ngOnInit(): void {
    this.loadCurrentUser();
  }

  loadCurrentUser(): void {
    if (isPlatformBrowser(this.platformId)) {
      const user = localStorage.getItem('currentUser');
      if (user) {
        this.CurrentUser = JSON.parse(user).name; 
      } else {
        this.CurrentUser = null; 
      }
    }
    
    this.updateNavbarContent();
    this.dataLoaded = true; 
  }

  updateNavbarContent(): void {
    if (this.CurrentUser) {
      this.navbarContent = [
        { label: 'Logout', action: () => this.logout() },
        { label: `${this.CurrentUser}`, isUser: true }
      ];
    } else {
      this.navbarContent = [
        { label: 'Sign Up', routerLink: '/auth/login' },
        { label: 'My Account', isUser: false }
      ];
    }
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('token');
    this.CurrentUser = null; 
    this.updateNavbarContent();

    // Navigate to login page
    this.router.navigate(['/auth/login']);
  }
}
