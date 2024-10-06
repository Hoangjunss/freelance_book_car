import { CommonModule, DOCUMENT, isPlatformBrowser } from '@angular/common';
import { Component, Inject, OnInit, PLATFORM_ID, Renderer2 } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../../services/user/user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
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

  navbarContent: { label: string, routerLink?: string, action?: (event?: MouseEvent) => void, isUser?: boolean }[] = [];
  dataLoaded: boolean = false;

  searchQuery: string = '';
  

  isMobileMenuOpen: boolean = false;
  constructor(private userService: UserService, private router: Router, @Inject(PLATFORM_ID) private platformId: Object,
    @Inject(DOCUMENT) private document: Document,
    private renderer: Renderer2
  ) { }

  ngOnInit(): void {
    this.loadCurrentUser();

    if (isPlatformBrowser(this.platformId)) {
      this.renderer.listen(this.document, 'click', (event: Event) => {
        const target = event.target as HTMLElement;
        const isClickInsideMenu = target.closest('#mobileMenu') || target.closest('.navbar-toggler');

        // Nếu không click bên trong menu, đóng menu
        if (!isClickInsideMenu && this.isMobileMenuOpen) {
          this.closeMobileMenu();
        }
      });
    }
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
    this.updateNavbarContent();

    // Navigate to login page
    this.router.navigate(['/auth/login']);
  }
  updateNavbarContent(): void {
    if (this.CurrentUser) {
      this.navbarContent = [
        { label: 'Logout', action: (event?: MouseEvent) => this.logout(event), isUser: false },
        { label: `${this.CurrentUser}`, isUser: true },
        { label: 'View History', routerLink: '/order-history', isUser: false },
      ];
    } else {
      this.navbarContent = [
        { label: 'Sign Up', routerLink: '/auth/login', isUser: false },
        { label: 'My Account', isUser: true },
        { label: 'View History', routerLink: '/order-history', isUser: false },
      ];
    }
  }

  onSearch() {
    if (this.searchQuery) {
      this.router.navigate(['/search'], { queryParams: { query: this.searchQuery } });
    }
  }

  navigateToHome(): void {
    this.router.navigate(['/home']).then(() => {
      window.location.reload();
    });
  }

  toggleMobileMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  closeMobileMenu() {
    this.isMobileMenuOpen = false;
  }

}
