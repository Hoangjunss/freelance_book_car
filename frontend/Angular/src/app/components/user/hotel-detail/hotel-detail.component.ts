import { Component, Input } from '@angular/core';
import { GetHotelDetailResponse } from '../../../models/response/product/hotel/hotel/get-hotel-detail-response';
import { ActivatedRoute } from '@angular/router';
import { HotelService } from '../../../services/product/hotel/hotel/hotel.service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from '../../../services/auth.interceptor';
import { UserService } from '../../../services/user/user.service';

@Component({
  selector: 'app-hotel-detail',
  standalone: true,
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    UserService
  ],
  imports: [HttpClientModule],
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
  

  constructor(private route: ActivatedRoute,private hotelService : HotelService) { }

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
}
