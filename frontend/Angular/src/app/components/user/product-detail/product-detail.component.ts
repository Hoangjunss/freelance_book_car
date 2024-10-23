import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TourService } from '../../../services/product/tour/tour/tour.service';
import { BookingService } from '../../../services/booking/booking.service';
import { TourScheduleService } from '../../../services/product/tour/tour-schedule/tour-schedule.service';
import { Title } from '@angular/platform-browser';
import { NotificationComponent } from '../../notification/notification.component';
import { GetTourResponse } from '../../../models/response/product/tour/tour/get-tour-response';
import { GetTourScheduleResponse } from '../../../models/response/product/tour/tour-schedule/get-tour-schedule-response';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule,NotificationComponent],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent implements OnInit {
  locationId: string | null = null;
  locations?: GetTourResponse;
  getTourScheduleResponse: GetTourScheduleResponse[] = [];
  availableTourSchedules: GetTourScheduleResponse[] = [];
  showFullText: boolean = false;


  @ViewChild(NotificationComponent) notificationComponent!: NotificationComponent;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private tourService: TourService,
    private bookingService: BookingService,
    private tourScheduleService: TourScheduleService,
    private titleService: Title,
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
          console.log("location : " + JSON.stringify(this.locations));
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
        if (response) {
          this.getTourScheduleResponse = response;
          console.log(this.getTourScheduleResponse);
          this.availableTourSchedules = this.getTourScheduleResponse.filter(x => x.priceTour > 0);
          console.log("availableTourSchedules : "+ JSON.stringify(this.availableTourSchedules));
        } else {
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

  showTooltip() {
    this.showFullText = true;
  }

  

}
