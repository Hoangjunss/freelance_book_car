import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { trigger, transition, style, animate, query, stagger } from '@angular/animations';
import { TourService } from '../../../services/product/tour/tour/tour.service';
import { GetTourResponse } from '../../../models/response/product/tour/tour/get-tour-response';

@Component({
  selector: 'app-location-list',
  imports:[CommonModule,RouterLink],
  standalone: true,
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']

})
export class LocationListComponent implements OnInit {
  location: string | null = null;
  locations: GetTourResponse [] = [];

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
    console.log("Địa điểm " + location);
    this.tourService.getTourByCategory(location).subscribe(response => {
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
