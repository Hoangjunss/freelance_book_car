import { CommonModule, JsonPipe } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../../services/user/user.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, RouterLink,FormsModule, ReactiveFormsModule, JsonPipe],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  userForm : FormGroup;

  constructor(private fb: FormBuilder,private router: Router,private titleService: Title,private userService: UserService) {
    this.userForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]], 
    });
    this.titleService.setTitle("Login");
  }

  loginUser() {
    if (this.userForm.valid) {
      const formData = new FormData();
      formData.append('email', this.userForm.value.email);
      formData.append('password', this.userForm.value.password);
      this.userService.loginUser(formData).subscribe({
        next: (response) => {
          console.log(response);
          const token = response.accessToken;
          if (token) {
            localStorage.setItem('token', token);
            this.getCurrentUser();
          }
          this.router.navigate(['/home']);
        },
        error: (error) => {
          console.error('Login error:', error);
        }
      });
    }
  }
  
  getCurrentUser() {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        console.log('User response:', user);
        localStorage.setItem('currentUser', JSON.stringify(user));
        console.log('Current user data saved:', user);
      },
      error: (error) => {
        console.error('Error fetching current user:', error);
      }
    });
  }
}
