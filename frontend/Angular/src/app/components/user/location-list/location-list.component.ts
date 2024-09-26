import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { trigger, transition, style, animate, query, stagger } from '@angular/animations';
import { TourService } from '../../../services/product/tour/tour/tour.service';

@Component({
  selector: 'app-location-list',
  imports:[CommonModule,RouterLink],
  standalone: true,
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']

})
export class LocationListComponent implements OnInit {
  location: string | null = null;
  locations: { name: string, imageUrl: string }[] = [];

  constructor(private route: ActivatedRoute, private router:Router,private tourService : TourService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.location = decodeURIComponent(params.get('location') || '');
      if (this.location) {
        this.getLocationsForSelectedLocation(this.location);
      }
    });
  }

  getLocationsForSelectedLocation(location: string) {
    this.tourService.getAllTour().subscribe(response => {
      console.log(response);
      if (response) {
        
        console.log("Thành công"); // Thông báo thành công
      } else {
        console.log("Thất bại"); // Thông báo thất bại
      }
    }, error => {
      console.log("Error:", error);
    });
  }

  goToLocationDetail(locationId: String) {
    this.router.navigate(['/location-details']);
  }
}
