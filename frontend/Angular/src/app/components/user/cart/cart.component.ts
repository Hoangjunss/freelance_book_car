import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from '../../../services/auth.interceptor';
import { UserService } from '../../../services/user/user.service';
import { GetBookingDetailResponse } from '../../../models/response/booking/get-booking-detail-response';
import { BookingService } from '../../../services/booking/booking.service';
import { GetBookingResponse } from '../../../models/response/booking/get-booking-response';
import { TourService } from '../../../services/product/tour/tour/tour.service';
import { HotelService } from '../../../services/product/hotel/hotel/hotel.service';
import { TicketService } from '../../../services/product/ticket/ticket/ticket.service';
import { TourismService } from '../../../services/product/ticket/tourism/tourism.service';
import { Title } from '@angular/platform-browser';


@Component({
  selector: 'app-cart',
  standalone: true,
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    UserService
  ],
  imports: [FormsModule,CommonModule,HttpClientModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit {
  
  getBookingDetailResponse: GetBookingDetailResponse [] = [];
  products:any[] = [];
  idBooking: number | null = null;

  constructor(private bookingService: BookingService,
    private tourService:TourService,
    private hotelService : HotelService,
    private tourismService : TourismService,
    private title: Title,
    @Inject(PLATFORM_ID) private platformId: Object
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
    return this.products.reduce((sum, product) => sum + (product.price * product.quantity), 0);
  }


  // Xóa các phương thức tăng/giảm số lượng vì đã chỉnh sửa giao diện
  increaseQuantity(product: any) {
    product.quantity++;
    this.updateProductQuantity(product);
  }

  decreaseQuantity(product: any) {
    if (product.quantity > 0) {
      product.quantity--;
      this.updateProductQuantity(product);
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
        if (response) {
          this.getBookingDetailResponse = response;
          this.products = this.getBookingDetailResponse.map(detail => ({
            idBookingDetail: detail.id,
            id: detail.idTour || detail.idHotel || detail.idTourism,
            name: detail.idTour ? 'Tour' : detail.idHotel ? 'Khách sạn' : 'Vé',
            price: detail.totalPrice,
            quantity: detail.quantity,
            image: 'https://via.placeholder.com/100', // You may want to set this dynamically based on type
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

  getTourDetail(id: number) {
    this.tourService.getTourDetailById(id).subscribe({
      next: (response) => {
        if (response) {
          // this.locations = [response]; 
        } else {
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
          // this.locations = [response];
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
          // this.locations = [response];
        } else {
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

  updateBookingType() {
    const type = 'PENDING'; 
    if (this.idBooking) {
      const confirmed = window.confirm("Bạn có chắc chắn muốn thanh toán không?");
      if (confirmed) {
        this.bookingService.updateTypeBooking(type, this.idBooking).subscribe({
          next: (response) => {
          },
          error: (error) => {
          }
        });
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

}
