import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [FormsModule,CommonModule],
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


  constructor() {
    // Initialize hours (0-23)
    this.hours = Array.from({ length: 24 }, (_, i) => i);

    // Initialize minutes (0-59)
    this.minutes = Array.from({ length: 60 }, (_, i) => (i) ? i : null).filter(n => n !== null);
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

  saveInfo() {
    console.log('Thông tin đã được lưu');
    this.toggleForm(); 
  }

  saveContactInfo(){
    console.log('Thông tin liên hệ đã được lưu');
    this.toggleContactForm();
  }
}
