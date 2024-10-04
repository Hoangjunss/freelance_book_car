import { GetTourismResponse } from './../../../models/response/product/ticket/tourism/get-tourism-response';
import { GetTourResponse } from './../../../models/response/product/tour/tour/get-tour-response';
import { GetHotelResponse } from './../../../models/response/product/hotel/hotel/get-hotel-response';
import { TicketService } from './../../../services/product/ticket/ticket/ticket.service';
import { GetHotelBookingResponse } from './../../../models/response/product/hotel/hotel-booking/get-hotelbooking-response';
import { Router } from '@angular/router';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from '../../../services/auth.interceptor';
import { UserService } from '../../../services/user/user.service';
import { GetBookingDetailResponse } from '../../../models/response/booking/get-booking-detail-response';
import { BookingService } from '../../../services/booking/booking.service';
import { TourService } from '../../../services/product/tour/tour/tour.service';
import { HotelService } from '../../../services/product/hotel/hotel/hotel.service';
import { TourismService } from '../../../services/product/ticket/tourism/tourism.service';
import { Title } from '@angular/platform-browser';
import { GetTicketResponse } from '../../../models/response/product/ticket/ticket/get-ticket-response';
import { GetTourScheduleResponse } from '../../../models/response/product/tour/tour-schedule/get-tour-schedule-response';
import { TourScheduleStatusService } from '../../../services/product/tour/tour-schedule-status/tour-schedule-status.service';
import { HotelbookingService } from '../../../services/product/hotel/hotelbooking/hotelbooking.service';
import { TourScheduleService } from '../../../services/product/tour/tour-schedule/tour-schedule.service';
import { forkJoin, Observable } from 'rxjs';


@Component({
  selector: 'app-cart',
  standalone: true,
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    UserService
  ],
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit {

  getBookingDetailResponse: GetBookingDetailResponse[] = [];
  getHotelBookingResponse: GetHotelBookingResponse[] = [];
  getTicketResponse: GetTicketResponse[] = [];
  getTourScheduleResposne: GetTourScheduleResponse[] = [];

  getHotelResponse: GetHotelResponse[]=[];
  getTourResponse: GetTourResponse[] =[];
  getTourismResponse: GetTourismResponse[]=[]; 
  

  products: any[] = [];
  idBooking: number | null = null;

  constructor(private bookingService: BookingService,
    private tourService: TourService,
    private tourScheduleService: TourScheduleService,
    private ticketService: TicketService,
    private hotelService: HotelService,
    private hotelBookingService: HotelbookingService,
    private tourismService: TourismService,
    private title: Title,
    @Inject(PLATFORM_ID) private platformId: Object,
    private router: Router
  ) { this.title.setTitle('Giỏ hàng'); }

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      const id = localStorage.getItem('idUser');
      if (id) {
        this.getBookingByUser(parseInt(id));
      }
    }
  }



  get total() {
    return this.products.reduce((sum, product) => sum + product.price, 0);
  }

  updateProductTotalPrice(product: any) {
    product.price = product.originalPrice * product.quantity;
  }


  increaseQuantity(product: any) {
    product.quantity++;
    this.updateProductQuantity(product);
    this.updateProductTotalPrice(product);
  }

  decreaseQuantity(product: any) {
    if (product.quantity > 1) {
      product.quantity--;
      this.updateProductQuantity(product);
      this.updateProductTotalPrice(product);
    }
  }



  getBookingByUser(id: number) {
    this.bookingService.getBookingByUser(id).subscribe({
      next: (response) => {
        if (response && response.id) {
          if (response.type === 'CART') {
            this.idBooking = response.id;
            this.getBookingDetail(response.id);
          } else {
            this.products = []; // Đặt sản phẩm thành mảng rỗng nếu không phải loại CART
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
        console.log(response);
        if (response) {
          const tourObservables: Observable<GetTourScheduleResponse>[] = [];
          const hotelObservables: Observable<GetHotelBookingResponse>[] = [];
          const ticketObservables: Observable<GetTicketResponse>[] = [];
          response.forEach((detail) => {
            if (detail.idTour != null) {
              tourObservables.push(this.tourScheduleService.getSchedule(detail.idTour));
            }
            if (detail.idHotel != null) {
              hotelObservables.push(this.hotelBookingService.getBooking(detail.idHotel));
            }
            if (detail.idTicket != null) {
              ticketObservables.push(this.ticketService.getTicket(detail.idTicket));
            }
          });
          forkJoin([...tourObservables, ...hotelObservables, ...ticketObservables]).subscribe(
            (results) => {
              const tourResults = results.slice(0, tourObservables.length);
              const hotelResults = results.slice(tourObservables.length, tourObservables.length + hotelObservables.length);
              const ticketResults = results.slice(tourObservables.length + hotelObservables.length);
              this.getTourScheduleResposne.push(...tourResults);
              this.getHotelBookingResponse.push(...hotelResults);
              this.getTicketResponse.push(...ticketResults);
              if (this.getTourScheduleResposne.length > 0) {
                this.getTourScheduleResposne.forEach((tour) => {
                  if (tour && tour.idTour) {
                    this.getTourDetail(tour.idTour);
                  }
                });
              } else {
                console.log("No tours available.");
              }
              this.getHotelBookingResponse.forEach((hotel) => {
                this.getHotelDetailById(hotel.hotel);
              });
  
              this.getTicketResponse.forEach((tourism) => {
                this.getTicketDetailById(tourism.idTourism);
              });
  
              console.log("Tour Schedule Response:", this.getTourScheduleResposne);
              console.log("Hotel Booking Response:", this.getHotelBookingResponse);
              console.log("Ticket Response:", this.getTicketResponse);
            },
            (error) => {
              console.error('Error in forkJoin:', error);
            }
          );
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

  getTourDetail(id: number) {
    this.tourService.getTourDetailById(id).subscribe({
      next: (response) => {
        if (response) {
          this.getTourResponse.push(response);
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

  getHotelDetailById(id: number) {
    this.hotelService.getHotelDetailById(id).subscribe({
      next: (response) => {
        if (response) {
          this.getHotelResponse.push(response);
        } else {
          console.log("Thất bại");
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

  getTicketDetailById(id: number) {
    this.tourismService.getTourismDetailById(id).subscribe({
      next: (response) => {
        if (response) {
          this.getTourismResponse.push(response);
        } else {
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

  updateBookingType() {
    const type = 'CART';
    if (this.idBooking) {
      const confirmed = window.confirm("Bạn có chắc chắn muốn thanh toán không?");
      if (confirmed) {
        this.router.navigate(['/booking']);
      }
    }
  }

  updateProductQuantity(product: any) {
    const formData = new FormData();
    formData.append('idBooking', product.idBookingDetail.toString());
    formData.append('quantity', product.quantity.toString());
    // Kiểm tra loại sản phẩm và thêm đúng tham số
    if (product.type === 'tour') {
      this.bookingService.updateQuantityTour(formData).subscribe({
        next: (response) => {
        },
        error: (error) => {
          console.log('Lỗi khi cập nhật số lượng tour', error);
        }
      });
    } else if (product.type === 'hotel') {
      this.bookingService.updateQuantityHotel(formData).subscribe({
        next: (response) => {
        },
        error: (error) => {
          console.log('Lỗi khi cập nhật số lượng khách sạn', error);
        }
      });
    } else if (product.type === 'ticket') {
      this.bookingService.updateQuantityTourism(formData).subscribe({
        next: (response) => {
        },
        error: (error) => {
          console.log('Lỗi khi cập nhật số lượng vé', error);
        }
      });
    }

  }

  removeProduct(product: any) {
    const confirmed = window.confirm('Bạn có chắc chắn muốn xóa sản phẩm này không?');
    if (confirmed) {
      this.products = this.products.filter(p => p.idBookingDetail !== product.idBookingDetail);
      this.bookingService.deleteBookingDetail(product.idBookingDetail).subscribe({
        next: (response) => {
          console.log('Sản phẩm đã được xóa thành công:', response);
        },
        error: (error) => {
          console.log('Lỗi khi xóa sản phẩm:', error);
        }
      });
    }
  }

}
