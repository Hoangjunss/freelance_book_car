import { CommonModule } from '@angular/common';
import { Component, OnInit} from '@angular/core';
import { RouterLink } from '@angular/router';
import { UserService } from '../../../services/user/user.service';


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule,RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {


}
