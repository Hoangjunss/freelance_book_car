import { Component, Input } from '@angular/core';
import { GetHotelDetailResponse } from '../../../models/response/product/hotel/hotel/get-hotel-detail-response';
import { ActivatedRoute, Router } from '@angular/router';
import { HotelService } from '../../../services/product/hotel/hotel/hotel.service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from '../../../services/auth.interceptor';
import { UserService } from '../../../services/user/user.service';
import { BookingService } from '../../../services/booking/booking.service';
import { AddBookingHotelRequest } from '../../../models/request/booking/add-booking-hotel-request';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-hotel-detail',
  standalone: true,
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    UserService
  ],
  imports: [HttpClientModule,CommonModule],
  templateUrl: './hotel-detail.component.html',
  styleUrl: './hotel-detail.component.css'
})
export class HotelDetailComponent {
  @Input() location: string | null = null;
  isExpanded = false; 
  locationId: string | null = null;
  locations: GetHotelDetailResponse[] = [];

  toggleContent(event: Event) {
    event.preventDefault(); 
    this.isExpanded = !this.isExpanded;
  }
  

  constructor(private route: ActivatedRoute,private hotelService : HotelService,private bookingService:BookingService,private router: Router) { }

  ngOnInit(): void {
    console.log("HotelDetailComponent initialized");
    console.log("hotel");
    this.route.paramMap.subscribe(params => {
      this.locationId = params.get('id');
      console.log("hotel"+this.locationId);
      if (this.locationId) {
        this.getHotelDetailById(parseInt(this.locationId));
      }
    });
  }

  getHotelDetailById(id: number) {
    this.hotelService.getHotelDetailById(id).subscribe(response => {
      console.log(response);
      if (response) {
        this.locations = [response];
      } else {
        console.log("Thất bại");
      }
    }, error => {
      console.log("Error:", error);
    });
  }

  addBookingHotel(locationId: string | null) {
    console.log("Add booking hotel");
    const id = locationId ? parseInt(locationId) : 0;
    const addBookingHotelRequest = new AddBookingHotelRequest();
    const idUser = localStorage.getItem('idUser');
    addBookingHotelRequest.idHotel = id;
    addBookingHotelRequest.idUser = idUser ? parseInt(idUser) : 0;
    addBookingHotelRequest.quantity = 1;
    addBookingHotelRequest.totalPrice = 100;

    // Chuyển đổi thành FormData
    const formData = new FormData();
    formData.append('idHotel', addBookingHotelRequest.idHotel.toString());
    formData.append('idUser', addBookingHotelRequest.idUser.toString());
    formData.append('quantity', addBookingHotelRequest.quantity.toString());
    formData.append('totalPrice', addBookingHotelRequest.totalPrice.toString());

    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });
    // Gọi service với FormData
    this.bookingService.addBookingHotel(formData).subscribe(response => {
        console.log(response);
        if (response) {
            console.log("Thành công");
            alert("Đặt phòng thành công");
            this.router.navigate(['/cart']);
        } else {
            console.log("Thất bại");
        }
    }, error => {
        console.log("Error:", error);
    });
}



}
