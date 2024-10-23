import { Component, Inject, OnInit, PLATFORM_ID, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { BookingService } from '../../../services/booking/booking.service';
import { Router } from '@angular/router';
import { TourService } from '../../../services/product/tour/tour/tour.service';
import { TourScheduleService } from '../../../services/product/tour/tour-schedule/tour-schedule.service';
import { NotificationComponent } from '../../notification/notification.component';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, HttpClientModule, NotificationComponent],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css'
})
export class PaymentComponent implements OnInit {
  products: any[] = [];
  idBooking: number | null = null;
  totalPrice: number | null = null;
  newTotalPrice: number | null = null;

  @ViewChild(NotificationComponent) notificationComponent!: NotificationComponent;

  constructor(
    private title: Title,
    @Inject(PLATFORM_ID) private platformId: Object,
    private bookingService: BookingService,
    private router: Router,
    private tourService: TourService,
    private tourScheduleService: TourScheduleService
  ) {
    this.title.setTitle('Thanh toán');
  }

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      const id = localStorage.getItem('idUser');
      if (id) {
        this.getBookingByUser(parseInt(id));
      }
    }
  }

  getBookingByUser(id: number) {
    this.bookingService.getBookingByUser(id).subscribe({
      next: (response) => {
        if (response && response.id) {
          if (response.type === 'CART') {
            this.idBooking = response.id;
            this.totalPrice = response.totalPrice;
            this.newTotalPrice = this.totalPrice;
            this.getBookingDetail(response.id);
          } else {
            this.products = [];
          }
        } else {
          console.log("No booking found for the user.");
        }
      },
      error: (error) => {
        console.log("Error fetching booking:", error);
      }
    });
  }

  getBookingDetail(id: number) {
    this.bookingService.getDetailBooking(id).subscribe({
      next: (response) => {
        console.log('Response:', response);
        if (response && response.length) {
          this.products = response;
        } else {
          this.products = [];
        }
      },
      error: (error) => {
        console.log("Error fetching booking details:", error);
      }
    });
  }

}
