import { Component } from '@angular/core';
import { NavigationEnd, NavigationStart, Router, RouterOutlet } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { NavbarComponent } from './components/user/navbar/navbar.component';
import { NavabarComponent } from "./components/admin/navabar/navabar.component";
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, LoginComponent, NavabarComponent,CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Angular';

  shouldShowNavbar = false;

  constructor(private router: Router) {
    // Lắng nghe sự kiện khi điều hướng kết thúc
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        // Cập nhật giá trị shouldShowNavbar dựa trên URL
        const url = event.urlAfterRedirects || event.url;
        this.shouldShowNavbar = !(url.startsWith('/auth') || url.startsWith('/admin'));
      });
  }

  
}
