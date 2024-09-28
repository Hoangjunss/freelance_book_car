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

@Component({
  selector: 'app-location-detail',
  standalone: true,
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    UserService
  ],
  imports: [CommonModule, HttpClientModule],
  templateUrl: './location-detail.component.html',
  styleUrls: ['./location-detail.component.css']
})
export class LocationDetailComponent {
  @Input() location: string | null = null;
  isExpanded = false;
  locationId: string | null = null;
  locations: GetTourResponse[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private tourService: TourService,
    private bookingService: BookingService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.locationId = params.get('id');
      console.log(this.locationId);
      if (this.locationId) {
        this.getTourDetail(parseInt(this.locationId));

      }
    });
  }

  getTourDetail(id: number) {
    this.tourService.getTourDetailById(id).subscribe({
      next: (response) => {
        console.log(response);
        if (response) {
          this.locations = [response];
        } else {
          console.log("Thất bại");
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

  addBookingTour(locationId: string | null) {
    console.log("addBookingTour");
    const id = locationId ? parseInt(locationId) : 0;
    console.log(id);
    const addBookingTourRequest = new AddBookingTourRequest();
    const idUser = localStorage.getItem('idUser');
    addBookingTourRequest.idUser = parseInt(idUser!);
    addBookingTourRequest.idTour = id;
    addBookingTourRequest.quantity = 1;
    addBookingTourRequest.totalPrice = 1000000;

    // Chuyển đổi thành FormData
    const formData = new FormData();
    formData.append('idTour', addBookingTourRequest.idTour.toString());
    formData.append('idUser', addBookingTourRequest.idUser.toString());
    formData.append('quantity', addBookingTourRequest.quantity.toString());
    formData.append('totalPrice', addBookingTourRequest.totalPrice.toString());

    this.bookingService.addBookingTour(formData).subscribe({
      next: (response) => {
        console.log(response);
        if (response) {
          console.log("Thành công");
          this.router.navigate(['/cart']);
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
