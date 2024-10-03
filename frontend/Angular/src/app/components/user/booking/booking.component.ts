import { CreateUserInfoRequest } from './../../../models/request/user/user-info/create-user-info-request';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from '../../../services/auth.interceptor';
import { UserService } from '../../../services/user/user.service';
import { Title } from '@angular/platform-browser';
import { GetBookingDetailResponse } from '../../../models/response/booking/get-booking-detail-response';
import { BookingService } from '../../../services/booking/booking.service';
import { Router } from '@angular/router';
import { OrderRequest } from '../../../models/request/booking/order-request';
import { OrderResponse } from '../../../models/response/booking/order-response';
import { CreateUserJoinRequest } from '../../../models/request/user/user-join/create-user-join-request';
import { TourService } from '../../../services/product/tour/tour/tour.service';
import { HotelService } from '../../../services/product/hotel/hotel/hotel.service';
import { TicketService } from '../../../services/product/ticket/ticket/ticket.service';
import { start } from 'repl';
import { GetTourResponse } from '../../../models/response/product/tour/tour/get-tour-response';
import { GetTourismResponse } from '../../../models/response/product/ticket/tourism/get-tourism-response';
import { GetHotelResponse } from '../../../models/response/product/hotel/hotel/get-hotel-response';
import { TourismService } from '../../../services/product/ticket/tourism/tourism.service';
import { HotelbookingService } from '../../../services/product/hotel/hotelbooking/hotelbooking.service';
import { TourScheduleService } from '../../../services/product/tour/tour-schedule/tour-schedule.service';

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
export class BookingComponent implements OnInit {
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
  totalPrice: number | null = null;

  orderRequest: OrderRequest = new OrderRequest();
  orderResponse: OrderResponse = new OrderResponse();
  createUserInfoRequest: CreateUserInfoRequest[] = []; 
  createUserInfo: CreateUserInfoRequest = new CreateUserInfoRequest();
  updateUserInfo: CreateUserInfoRequest = new CreateUserInfoRequest();
  createUserJoinRequest: CreateUserJoinRequest[] = [];
  createUserJoin: CreateUserJoinRequest = new CreateUserJoinRequest();
  updateUserJoin: CreateUserJoinRequest = new CreateUserJoinRequest();

  getTour: GetTourResponse[] = [];
  getTourism: GetTourismResponse[] = [];
  getHotel: GetHotelResponse[] = [];

  selectedContactIndex: number | null = null;
  selectedUserIndex: number | null = null;

  constructor(
    private title: Title,
    @Inject(PLATFORM_ID) private platformId: Object,
    private bookingService: BookingService,
    private router: Router,
    private tourService: TourService,
    private tourScheduleService: TourScheduleService,
    private tourismService: TourismService,
    private hotelService: HotelService,
    private hotelBookingService: HotelbookingService,
    private ticketService: TicketService,
  ) {
    this.hours = Array.from({ length: 24 }, (_, i) => i);

    this.minutes = Array.from({ length: 60 }, (_, i) => (i) ? i : null).filter(n => n !== null);

    this.title.setTitle('Điền thông tin');
  }
  
  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      const id = localStorage.getItem('idUser');
      if (id) {
        this.getBookingByUser(parseInt(id));
        console.log('ID:', this.products);
      }
    }
  }

  updateCharCount() {
    this.charCount = this.specialRequest.length;
  }

  toggleForm() {
    this.showForm = !this.showForm;
    if (!this.showForm) {
      this.createUserJoin = new CreateUserJoinRequest();
      this.selectedUserIndex = null;
    }
  }
  
  toggleContactForm() {
    this.showContactForm = !this.showContactForm;
    if (!this.showContactForm) {
      this.createUserInfo = new CreateUserInfoRequest();
      this.selectedContactIndex = null;
    }
  }

  toggleEditForm() {
    this.showEditForm = !this.showEditForm;
  }
  toggleContactEditForm(){
    this.showContactEditForm = !this.showContactEditForm;
  }

  saveInfo() {
    if (this.selectedUserIndex === null) {
      this.createUserJoinRequest.push({ ...this.createUserJoin });
    } else {
      this.createUserJoinRequest[this.selectedUserIndex] = { ...this.createUserJoin };
      this.selectedUserIndex = null;
    }
    this.createUserJoin = new CreateUserJoinRequest();
    this.toggleForm();
  }

  editUser(index: number) {
    this.selectedUserIndex = index; 
    this.createUserJoin = { ...this.createUserJoinRequest[index] };
    this.showForm = true;
  }

  deleteUser(index: number) {
    this.createUserJoinRequest.splice(index, 1);
  }

  saveContactInfo() {
    if (this.selectedContactIndex === null) {
      this.createUserInfoRequest.push({ ...this.createUserInfo });
    } else {
      this.createUserInfoRequest[this.selectedContactIndex] = { ...this.createUserInfo };
      this.selectedContactIndex = null;
    }
    this.createUserInfo = new CreateUserInfoRequest();
    this.toggleContactForm();
  }

  editContact(index: number) {
    this.selectedContactIndex = index;
    this.createUserInfo = { ...this.createUserInfoRequest[index] };
    this.showContactForm = true; 
  }

  deleteContact(index: number) {
    this.createUserInfoRequest.splice(index, 1);
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
          if (response.type === 'CART') {
            this.idBooking = response.id;
            this.getBookingDetail(response.id);
            this.totalPrice = response.totalPrice;
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
          console.log(response);
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

          if (this.products.some(p => p.type === 'tour')) {
            this.products.filter(p => p.type === 'tour').forEach(p => {
              this.tourService.getTourById(p.id).subscribe({
                next: (response) => {
                  console.log('Tour:', response);
                  p.startLocation = response.startLocation;
                  p.endLocation = response.endLocation;
                  p.image = response.image;
                  p.name = response.name;
                },
                error: (error) => {
                  console.log("Error:", error);
                }
              });
            });
          }

          if (this.products.some(p => p.type === 'hotel')) {
            this.products.filter(p => p.type === 'hotel').forEach(p => {
              this.hotelService.getHotelDetailById(p.id).subscribe({
                next: (response) => {
                  console.log('Hotel:', response);
                  p.name = response.name;
                  p.image = response.image;
                  p.location = response.location;
            

                },
                error: (error) => {
                  console.log("Error:", error);
                }
              });
            });
          }

          if (this.products.some(p => p.type === 'ticket')) {
            this.products.filter(p => p.type === 'ticket').forEach(p => {
              this.ticketService.getTicket(p.id).subscribe({
                next: (response) => {
                  console.log('Ticket:', response);
                  p.name = response.startDate;
                },
                error: (error) => {
                  console.log("Error:", error);
                }
              });
            });
          }
        } else {
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

  onPayment(){
    const formData = new FormData();
    const idUser = localStorage.getItem('idUser');
    const idBooking = localStorage.getItem('idBooking');
    if(this.createUserInfoRequest == null){
      alert('Vui lòng điền ít nhất một thông tin liên hệ');
      return;
    }
    if(this.createUserJoinRequest == null){
      alert('Vui lòng điền ít nhất một thông tin người tham gia');
      return;
    }
    

    formData.append('id', idBooking);
    formData.append('idUser', idUser);
    formData.append('dateBook', new Date().toISOString().slice(0, 19));
    formData.append('totalPrice', this.totalPrice.toString());
    formData.append('createUserInfoRequest', JSON.stringify(this.createUserInfoRequest));
    formData.append('createUserJoinRequest', JSON.stringify(this.createUserJoinRequest));
    formData.append('paymentMethod', 1+"");

    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });

    this.bookingService.order(formData).subscribe({
      next: (response) => {
        if(response){
          this.updateTypeBooking(parseInt(idBooking));
          alert("Bạn đã đặt thành công. Vui lòng kiểm tra Email và chờ phản hồi");
        }
      },
      error: (error) => {
        console.log('Payment error:', error);
      }
    })
  }

  updateTypeBooking(id: number) {
    this.bookingService.updateTypeBooking('PENDING', id).subscribe({
      next: (value) => {
        if (value) {
          console.log('successful');
          this.router.navigate(['order-history']); 
          localStorage.removeItem('idBooking');
        }
      },
    });
  }
  
}
