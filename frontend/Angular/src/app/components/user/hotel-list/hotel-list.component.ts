import { Component } from '@angular/core';
import { GetHotelResponse } from '../../../models/response/product/hotel/hotel/get-hotel-response';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HotelService } from '../../../services/product/hotel/hotel/hotel.service';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from '../../../services/auth.interceptor';
import { UserService } from '../../../services/user/user.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-hotel-list',
  standalone: true,
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    UserService
  ],
  imports: [CommonModule,RouterLink,HttpClientModule],
  templateUrl: './hotel-list.component.html',
  styleUrl: './hotel-list.component.css'
})
export class HotelListComponent {
  location: string | null = null;
  locations: GetHotelResponse [] = [];

  constructor(private route: ActivatedRoute, private router:Router,private hotelService : HotelService,private title:Title) {
    this.title.setTitle("Danh sách khách sạn");
   }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.location = decodeURIComponent(params.get('location') || '');
      if (this.location) {
        this.getHotelByLocation(this.location);
      }
    });
  }

  getHotelByLocation(location: string) {
    console.log("Địa điểm " + location);
    this.hotelService.getHotelByLocation(location).subscribe(response => {
      console.log(response);
      
      if (response) {
        this.locations = response;
        console.log("Thành công");
      } else {
        console.log("Thất bại");
      }
    }, error => {
      console.log("Error:", error);
    });
  }

  goToLocationDetail(locationId?: number) {
    const path = `/hotel-details/${locationId}`;
    console.log('Navigating to:', path);
    this.router.navigate([path]);
    console.log('Navigating to:', path);
  }
}
