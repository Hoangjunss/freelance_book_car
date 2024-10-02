import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from '../../../services/auth.interceptor';
import { UserService } from '../../../services/user/user.service';
import { Title } from '@angular/platform-browser';
import { GetBookingDetailResponse } from '../../../models/response/booking/get-booking-detail-response';
import { BookingService } from '../../../services/booking/booking.service';
import { Router } from '@angular/router';

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
  getBookingDetailResponse: GetBookingDetailResponse [] = [];
  products:any[] = [];
  idBooking: number | null = null;

  constructor(private title: Title,@Inject(PLATFORM_ID) private platformId: Object,private bookingService: BookingService,private router: Router) {
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
    this.toggleForm(); 
  }

  saveContactInfo(){
    this.toggleContactForm();
  }

  updateInfo() {
    this.toggleEditForm();
  }

  updateContactInfo(){
    this.toggleContactEditForm();
  }

  getBookingByUser(id: number) {
    this.bookingService.getBookingByUser(id).subscribe({
      next: (response) => {
        if (response && response.id) {
          if (response.type === 'PENDING') {
            this.idBooking = response.id;
            this.getBookingDetail(response.id);
          } else {
            this.products = []; 
          }
          
        } else {
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

  getBookingDetail(id: number) {
    this.bookingService.getDetailBooking(id).subscribe({
      next: (response) => {
        if (response) {
          this.getBookingDetailResponse = response;
          this.products = this.getBookingDetailResponse.map(detail => ({
            idBookingDetail: detail.id,
            id: detail.idTour || detail.idHotel || detail.idTourism,
            name: detail.idTour ? 'Tour' : detail.idHotel ? 'Khách sạn' : 'Vé',
            price: detail.totalPrice,
            quantity: detail.quantity,
            image: 'https://via.placeholder.com/100',
            type: detail.idTour ? 'tour' : detail.idHotel ? 'hotel' : 'ticket'
          }));
        } else {
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }
}
