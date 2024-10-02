import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TourService } from '../../../services/product/tour/tour/tour.service';
import { GetTourResponse } from '../../../models/response/product/tour/tour/get-tour-response';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from '../../../services/auth.interceptor';
import { UserService } from '../../../services/user/user.service';
import { BookingService } from '../../../services/booking/booking.service';
import { AddBookingTourRequest } from '../../../models/request/booking/add-booking-tour-request';
import { GetTourScheduleResponse } from '../../../models/response/product/tour/tour-schedule/get-tour-schedule-response';
import { TourScheduleService } from '../../../services/product/tour/tour-schedule/tour-schedule.service';
import { FormsModule } from '@angular/forms';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-location-detail',
  standalone: true,
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    UserService
  ],
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.css']
})
export class LocationDetailComponent {
  @Input() location: string | null = null;
  isExpanded = false;
  locationId: string | null = null;
  locations?: GetTourResponse;
  getTourScheduleResponse: GetTourScheduleResponse[] = [];
  availableTourSchedules: GetTourScheduleResponse[] = [];
  selectedTourSchedule: number | null = null;
  selectedPrice: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private tourService: TourService,
    private bookingService: BookingService,
    private tourScheduleService: TourScheduleService,
    private titleService: Title
  ) { this.titleService.setTitle("Location-detail"); }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.locationId = params.get('id');
      if (this.locationId) {
        this.getTourDetail(parseInt(this.locationId));
      }
    });
  }

  getTourDetail(id: number) {
    this.tourService.getTourDetailById(id).subscribe({
      next: (response) => {
        if (response) {
          this.locations = response;
          if (this.locations.id !== undefined && this.locations.id !== null) {
            this.getTourScheduleByidTour(this.locations.id);
          }
        } else {
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

  getTourScheduleByidTour(id: number) {
    this.tourScheduleService.getTourScheduleByidTour(id).subscribe({
      next: (response) => {
        const currentDate = new Date();
        if (response) {
          this.getTourScheduleResponse = response;
          this.availableTourSchedules = response.filter(schedule => {
            if (schedule.timeStartTour !== undefined) {
              const scheduleDate = new Date(schedule.timeStartTour);
              return scheduleDate >= currentDate; // Trả về true nếu ngày lịch trình lớn hơn hoặc bằng ngày hiện tại
            }
            return false; // Trả về false nếu timeStartTour là undefined
          });
        }
      }
    });
  }


  onTourScheduleChange() {
    // Lấy ra thông tin chi tiết của lịch trình đã chọn
    const selectedSchedule = this.availableTourSchedules.find(schedule => {
      return schedule.id === Number(this.selectedTourSchedule); // Chuyển selectedTourSchedule thành số
    });

    if (selectedSchedule) {
      this.selectedPrice = selectedSchedule.priceTour ?? null; // Cập nhật giá
    } else {
      this.selectedPrice = null; // Nếu không có lịch trình, đặt giá thành null
    }
  }




  addBookingTour(locationId: string | null) {

    if (!this.selectedTourSchedule) {
      alert("Please select tour itinerary")
      return;
    }

    const id = locationId ? parseInt(locationId) : 0;
    const addBookingTourRequest = new AddBookingTourRequest();
    const idUser = localStorage.getItem('idUser');
    const idBooking = localStorage.getItem('idBooking');
    addBookingTourRequest.idUser = parseInt(idUser!);
    addBookingTourRequest.idTour = id;
    addBookingTourRequest.quantity = 1;


    const selectedSchedule = this.availableTourSchedules.find(schedule => {
      return schedule.id === Number(this.selectedTourSchedule);
    });


    if (selectedSchedule) {
      addBookingTourRequest.totalPrice = selectedSchedule.priceTour;
    } else {
      addBookingTourRequest.totalPrice = 0;
      console.error('Selected tour schedule is null or not found');
      return;
    }

    // Chuyển đổi thành FormData
    const formData = new FormData();
    formData.append('idTour', this.selectedTourSchedule?.toString() || '');
    formData.append('idUser', addBookingTourRequest.idUser.toString());
    formData.append('idBooking', idBooking || '');
    formData.append('quantity', addBookingTourRequest.quantity.toString());
    formData.append('totalPrice', addBookingTourRequest.totalPrice ? addBookingTourRequest.totalPrice.toString() : '');


    this.bookingService.addBookingTour(formData).subscribe({
      next: (response) => {
        if (response) {

          if (idBooking == null) {
            console.log(response);
            localStorage.setItem('idBooking', response.idBooking + "");
          }
          alert('ThanhCong');
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

  toggleContent(event: Event) {
    event.preventDefault();
    this.isExpanded = !this.isExpanded;
  }
}
