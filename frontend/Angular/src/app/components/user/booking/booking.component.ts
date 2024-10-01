import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from '../../../services/auth.interceptor';
import { UserService } from '../../../services/user/user.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-booking',
  standalone: true,
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    UserService
  ],
  imports: [FormsModule,CommonModule,HttpClientModule],
  templateUrl: './booking.component.html',
  styleUrl: './booking.component.css'
})
export class BookingComponent {
  specialRequest: string = ''; 
  charCount: number = 0; 
  hours: number[] = [];
  minutes: number[] = [];
  showForm: boolean = false;
  showContactForm: boolean = false;
  showEditForm: boolean = false;
  showContactEditForm: boolean = false;

  constructor(private title: Title) {
    // Initialize hours (0-23)
    this.hours = Array.from({ length: 24 }, (_, i) => i);

    // Initialize minutes (0-59)
    this.minutes = Array.from({ length: 60 }, (_, i) => (i) ? i : null).filter(n => n !== null);

    this.title.setTitle('Điền thông tin');
  }

  updateCharCount() {
    this.charCount = this.specialRequest.length;
  }

  toggleForm() {
    this.showForm = !this.showForm;
  }
  toggleContactForm(){
    this.showContactForm = !this.showContactForm;
  }
  toggleEditForm() {
    this.showEditForm = !this.showEditForm;
  }
  toggleContactEditForm(){
    this.showContactEditForm = !this.showContactEditForm;
  }

  saveInfo() {
    console.log('Thông tin đã được lưu');
    this.toggleForm(); 
  }

  saveContactInfo(){
    console.log('Thông tin liên hệ đã được lưu');
    this.toggleContactForm();
  }

  updateInfo() {
    this.toggleEditForm();
  }

  updateContactInfo(){
    this.toggleContactEditForm();
  }
}
