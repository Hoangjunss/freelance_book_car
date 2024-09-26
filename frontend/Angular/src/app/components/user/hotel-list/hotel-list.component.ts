import { Component } from '@angular/core';
import { GetHotelResponse } from '../../../models/response/product/hotel/hotel/get-hotel-response';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HotelService } from '../../../services/product/hotel/hotel/hotel.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-hotel-list',
  standalone: true,
  imports: [CommonModule,RouterLink],
  templateUrl: './hotel-list.component.html',
  styleUrl: './hotel-list.component.css'
})
export class HotelListComponent {
  location: string | null = null;
  locations: GetHotelResponse [] = [];

  constructor(private route: ActivatedRoute, private router:Router,private hotelService : HotelService) { }

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
