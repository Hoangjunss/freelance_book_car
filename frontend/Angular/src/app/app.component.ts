import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { NavbarComponent } from './components/user/navbar/navbar.component';
import { NavabarComponent } from "./components/admin/navabar/navabar.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, LoginComponent, NavabarComponent,CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Angular';
  constructor(public router: Router) { }

  
}
