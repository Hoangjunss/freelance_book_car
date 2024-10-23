import { Component, Inject, OnInit, PLATFORM_ID, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { BookingService } from '../../../services/booking/booking.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TourService } from '../../../services/product/tour/tour/tour.service';
import { TourScheduleService } from '../../../services/product/tour/tour-schedule/tour-schedule.service';
import { NotificationComponent } from '../../notification/notification.component';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { VnpayService } from '../../../services/payment/VNPAY/vnpay.service';
import { PaypalService } from '../../../services/payment/Paypal/paypal.service';

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
  vnp_ResponseCode: string | null = null;

  @ViewChild(NotificationComponent) notificationComponent!: NotificationComponent;

  constructor(
    private title: Title,
    @Inject(PLATFORM_ID) private platformId: Object,
    private bookingService: BookingService,
    private router: Router,
    private tourService: TourService,
    private tourScheduleService: TourScheduleService,
    private vnpayService: VnpayService,
    private paypalService:PaypalService,
    private route: ActivatedRoute
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

  payWithVNPAY() {
    if (this.totalPrice && this.idBooking) {
      const convertedPrice = Math.round(this.totalPrice * 100);
      this.vnpayService.payWithVNPAY(convertedPrice, this.idBooking);
    } else {
      console.error('No valid totalPrice or idBooking found');
    }
  }

  payWithPayPal() {
    const formData = new FormData();
    formData.append('price', '1');
    formData.append('currency', 'VND');
    formData.append('method', 'paypal');
    formData.append('intent','SALE' );
    formData.append('description', 'abc');

    this.paypalService.payWithPayPal(formData).subscribe({
      
    });

  }



  

}
