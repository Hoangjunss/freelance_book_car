import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GetTourismResponse } from '../../../models/response/product/ticket/tourism/get-tourism-response';
import { TourismService } from '../../../services/product/ticket/tourism/tourism.service';

@Component({
  selector: 'app-ticket-list',
  standalone: true,
  imports: [],
  templateUrl: './ticket-list.component.html',
  styleUrl: './ticket-list.component.css'
})
export class TicketListComponent {
  location: string | null = null;
  locations: GetTourismResponse [] = [];

  constructor(private route: ActivatedRoute, private router:Router,private tourismService : TourismService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.location = decodeURIComponent(params.get('location') || '');
      if (this.location) {
        this.getTourismByCategory(this.location);
      }
    });
  }

  getTourismByCategory(location: string) {
    console.log("Địa điểm " + location);
    this.tourismService.getTourismByCategory(location).subscribe(response => {
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
    debugger;
    const path = `/location-details/${locationId}`;
    console.log('Navigating to:', path);
    this.router.navigate([path]);
    console.log('Navigating to:', path);
  }
}
