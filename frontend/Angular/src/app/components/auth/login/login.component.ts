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
      this.userService.loginUser(formData).subscribe((response) => {
        console.log(response);
        this.router.navigate(['/home']);
      }, (error) => {
        console.log(error);
        if (error.status === 404) {
          // Thêm lỗi nếu email không tồn tại
          this.userForm.get('email')?.setErrors({ emailNotFound: true });
          console.error('Account not found');
        } 
        else if (error.status === 500) {
          // Thêm lỗi nếu mật khẩu không đúng
          this.userForm.get('password')?.setErrors({ invalidCredentials: true });
          console.error('Password not machines');
        }
      });
    }
  }
}
