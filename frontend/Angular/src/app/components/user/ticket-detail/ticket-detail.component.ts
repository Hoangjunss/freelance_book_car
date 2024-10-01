import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { GetTourismDetailResponse } from '../../../models/response/product/ticket/tourism/get-tourism-detail-response';
import { ActivatedRoute } from '@angular/router';
import { TourismService } from '../../../services/product/ticket/tourism/tourism.service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from '../../../services/auth.interceptor';
import { UserService } from '../../../services/user/user.service';
import { TicketService } from '../../../services/product/ticket/ticket/ticket.service';
import { GetTicketResponse } from '../../../models/response/product/ticket/ticket/get-ticket-response';
import { FormsModule } from '@angular/forms';
import { BookingService } from '../../../services/booking/booking.service';
import { AddBookingTourismRequest } from '../../../models/request/booking/add-booking-tourism-request';
import { Title } from '@angular/platform-browser';
import e from 'express';

@Component({
  selector: 'app-ticket-detail',
  standalone: true,
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    UserService
  ],
  imports: [CommonModule,HttpClientModule, FormsModule],
  templateUrl: './ticket-detail.component.html',
  styleUrl: './ticket-detail.component.css'
})
export class TicketDetailComponent {

  @Input() location: string | null = null;
  isExpanded = false; 
  locationId: string | null = null;
  locations?: GetTourismDetailResponse;
  getTicketResponse?: GetTicketResponse[] =[];
  availableTourSchedules: GetTicketResponse[] = [];
  selectedTourSchedule:number | null = null; 
  selectedPrice: number | null = null;


  toggleContent(event: Event) {
    event.preventDefault(); 
    this.isExpanded = !this.isExpanded;
  }
  

  constructor(private route: ActivatedRoute,private tourismService : TourismService, 
    private ticketService: TicketService,
    private bookingService: BookingService,
    private titleService: Title
  ) { this.titleService.setTitle("Chi tiết đặt vé");}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.locationId = params.get('id');
      if (this.locationId) {
        this.getTourismDetailById(parseInt(this.locationId));
        this.getTicketByIdTourism(parseInt(this.locationId));
      }
    });
  }

  onTourScheduleChange() {
    const selectedSchedule = this.availableTourSchedules.find(schedule => {
      return schedule.id === Number(this.selectedTourSchedule); // Chuyển selectedTourSchedule thành số
    });

    if (selectedSchedule) {
      this.selectedPrice = selectedSchedule.tourPrice || null;
    }
    else {
      this.selectedPrice = null;
    }

  }

  checkServiceStatus() {
    // Logic để kiểm tra trạng thái dịch vụ với selectedTourSchedule
  }

  getTicketByIdTourism(id: number) {
    this.ticketService.getTicketByIdTourism(id).subscribe({
      next: (response) => {
        if (response) {
          this.getTicketResponse = response;
          this.availableTourSchedules = response; // Đảm bảo điều này khớp với template của bạn
        }
      },
      error: (err) => console.error('Lỗi khi lấy vé:', err)
    });
  }

  getTourismDetailById(id: number) {
    this.tourismService.getTourismDetailById(id).subscribe(response => {
      if (response) {
        this.locations = response;
      } else {
        console.log("Thất bại");
      }
    }, error => {
      console.log("Error:", error);
    });
  }

  addBookingTour(locationId: string | null) {
    const id = locationId ? parseInt(locationId) : 0;
    const addBookingTourRequest = new AddBookingTourismRequest();
    const idUser = localStorage.getItem('idUser');
    addBookingTourRequest.idUser = parseInt(idUser!);
    addBookingTourRequest.idTicket = id;
    addBookingTourRequest.quantity = 1;

    const selectedSchedule = this.availableTourSchedules.find(schedule => {
      return schedule.id === Number(this.selectedTourSchedule); // Chuyển selectedTourSchedule thành số
    });

    if(selectedSchedule) {
      addBookingTourRequest.totalPrice = selectedSchedule.tourPrice;
    }else {
      addBookingTourRequest.totalPrice = 0;
    }

    // Chuyển đổi thành FormData
    const formData = new FormData();
    formData.append('idTicket', this.selectedTourSchedule?.toString() || '');
    formData.append('idUser', addBookingTourRequest.idUser.toString());
    formData.append('quantity', addBookingTourRequest.quantity.toString());
    formData.append('totalPrice', addBookingTourRequest.totalPrice ? addBookingTourRequest.totalPrice.toString() : ''); 

    this.bookingService.addBookingTourism(formData).subscribe({
      next: (response) => {
        if (response) {
          alert('ThanhCong');
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

}
