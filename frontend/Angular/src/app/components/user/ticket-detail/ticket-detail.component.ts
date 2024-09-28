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
  selectedTourSchedule!:number;


  toggleContent(event: Event) {
    event.preventDefault(); 
    this.isExpanded = !this.isExpanded;
  }
  

  constructor(private route: ActivatedRoute,private tourismService : TourismService, 
    private ticketService: TicketService,
    private bookingService: BookingService) { }

  ngOnInit(): void {
    console.log("tourismDetailComponent initialized");
    this.route.paramMap.subscribe(params => {
      this.locationId = params.get('id');
      console.log("tourism "+this.locationId);
      if (this.locationId) {
        this.getTourismDetailById(parseInt(this.locationId));
        this.getTicketByIdTourism(parseInt(this.locationId));
      }
    });
  }

  onTourScheduleChange() {
    console.log('Selected Tour Schedule ID:', this.selectedTourSchedule);
    // Bạn có thể thực hiện các hành động khác khi người dùng thay đổi lựa chọn
  }

  checkServiceStatus() {
    // Logic để kiểm tra trạng thái dịch vụ với selectedTourSchedule
    console.log('Checking service status for schedule ID:', this.selectedTourSchedule);
  }

  getTicketByIdTourism(id: number) {
    this.ticketService.getTicketByIdTourism(id).subscribe({
      next: (response) => {
        if (response) {
          console.log('Phản hồi vé:', response); // Ghi lại phản hồi
          this.getTicketResponse = response;
          this.availableTourSchedules = response; // Đảm bảo điều này khớp với template của bạn
        }
      },
      error: (err) => console.error('Lỗi khi lấy vé:', err)
    });
  }

  getTourismDetailById(id: number) {
    this.tourismService.getTourismDetailById(id).subscribe(response => {
      console.log(response);
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
    console.log("addBookingTour");
    const id = locationId ? parseInt(locationId) : 0;
    console.log(id);
    const addBookingTourRequest = new AddBookingTourismRequest();
    const idUser = localStorage.getItem('idUser');
    addBookingTourRequest.idUser = parseInt(idUser!);
    addBookingTourRequest.idTicket = id;
    addBookingTourRequest.quantity = 1;
    addBookingTourRequest.totalPrice = 1000;

    // Chuyển đổi thành FormData
    const formData = new FormData();
    formData.append('idTicket', this.selectedTourSchedule.toString());
    formData.append('idUser', addBookingTourRequest.idUser.toString());
    formData.append('quantity', addBookingTourRequest.quantity.toString());
    formData.append('totalPrice', addBookingTourRequest.totalPrice.toString()); //Chua fetch gia

    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });

    this.bookingService.addBookingTourism(formData).subscribe({
      next: (response) => {
        console.log(response);
        if (response) {
          console.log("Thành công");
          alert('ThanhCong');
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

}
