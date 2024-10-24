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
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, HttpClientModule, NotificationComponent, FormsModule],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css'
})
export class PaymentComponent implements OnInit {
  products: any[] = [];
  idBooking: number | null = null;
  totalPrice: number | null = null;
  newTotalPrice: number | null = null;
  vnp_ResponseCode: string | null = null;
  selectedPaymentMethod: string = 'vnpay';

  @ViewChild(NotificationComponent) notificationComponent!: NotificationComponent;

  constructor(
    private title: Title,
    @Inject(PLATFORM_ID) private platformId: Object,
    private bookingService: BookingService,
    private router: Router,
    private tourService: TourService,
    private tourScheduleService: TourScheduleService,
    private vnpayService: VnpayService,
    private paypalService: PaypalService,
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
        if (response && response.length) {
          this.products = response;
          console.log("products", this.products);
          this.products.forEach((product) => {
            this.tourScheduleService.getSchedule(product.idTour).subscribe({
              next: (schedule) => {
                if (schedule) {
                  product.product = schedule;
                  console.log("product", product);
                  this.tourService.getTourById(product.idTour).subscribe({
                    next: (tour) => {
                      if (tour) {
                        console.log("tour", tour);
                        product.name = tour.name;
                      }
                    },
                    error: (error) => {
                      console.log("Error fetching schedule:", error);
                    }
                  });
                }
              },
              error: (error) => {
                console.log("Error fetching tour:", error);
              }
            });
          });

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
    formData.append('price', this.totalPrice.toString());
    formData.append('currency', 'USD');
    formData.append('method', 'paypal');
    formData.append('intent', 'SALE');
    formData.append('description', 'Thanh toán đơn hàng');

    this.paypalService.payWithPayPal(formData).subscribe({

    });

  }

  onPaymentMethodChange(method: string) {
    this.selectedPaymentMethod = method;
  }

  confirmPayment() {
    if (this.selectedPaymentMethod === 'vnpay') {
      this.payWithVNPAY();
    } else if (this.selectedPaymentMethod === 'paypal') {
      this.payWithPayPal();
    }
  }





}
