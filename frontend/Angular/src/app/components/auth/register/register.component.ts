import { CommonModule, JsonPipe } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, RouterLink,FormsModule, ReactiveFormsModule, JsonPipe],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  userForm: FormGroup;
  constructor(private fb: FormBuilder, private router: Router,private titleService: Title) { 
    this.userForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, this.passwordValidator()]]
    });
    this.titleService.setTitle("Register");
  }

  ngOnInit(): void {
  }

  // Kiểm tra password có chứa ít nhất 1 chữ cái, 1 số và 1 ký tự đặc biệt hay không
  passwordValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (!value) return null; // Nếu giá trị của control là rỗng thì trả về null

      // Kiểm tra xem password có chứa ít nhất 1 chữ cái, 1 số và 1 ký tự đặc biệt hay không
      const hasLetter = /[a-zA-Z]/.test(value);
      const hasDigit = /\d/.test(value);
      const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(value);
      const isValidLength = value.length >= 8;

      // Tạo đổi tượng errors để chứa các lỗi của form
      const errors: ValidationErrors = {};// ValidationError là 1 đối tượng chứa các lỗi của form và là đối tượng JavaScript với các cặp key-value tương ứng với tên lỗi và giá trị lỗi

      // Check each condition
      if (!hasLetter) errors['noLetter'] = true;
      if (!hasDigit) errors['noDigit'] = true;
      if (!hasSpecialChar) errors['noSpecialChar'] = true;
      if (!isValidLength) errors['invalidLength'] = true;

      console.log(Object.keys(errors)); // trả về mảng các key của object errors

      // Nếu có lỗi thì trả về object errors, ngược lại trả về null
      return Object.keys(errors).length ? errors : null;
    };
  }

  // Lấy thông báo lỗi của email
  getPasswordErrorMessage(): string {
    const control = this.userForm.get('password');
    const errors = [];

    if (control?.hasError('required')) {
      errors.push(' is required');
    }
    if (control?.hasError('invalidLength')) {
      errors.push('must be at least 8 characters long');
    }
    if (control?.hasError('noLetter')) {
      errors.push('must contain at least one letter');
    }
    if (control?.hasError('noDigit')) {
      errors.push('must contain at least one number');
    }
    if (control?.hasError('noSpecialChar')) {
      errors.push('must contain at least one special character');
    }

    // Nếu có lỗi thì trả về chuỗi thông báo lỗi, ngược lại trả về chuỗi rỗng
    return errors.length ? `Password ${errors.join(' and ')}` : '';
  }

  registerUser() {
    if (this.userForm.valid) {
      console.log(this.userForm.value + " submitForm"); 
      console.log(this.userForm.value.name + " " + this.userForm.value.email + " " + this.userForm.value.password);
    }
    else {
      console.log("Form is invalid");
    }
  }

}
