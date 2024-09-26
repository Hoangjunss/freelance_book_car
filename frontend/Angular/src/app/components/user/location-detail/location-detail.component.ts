import { CommonModule } from '@angular/common';
import { Component,Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TourService } from '../../../services/product/tour/tour/tour.service';
import { GetTourResponse } from '../../../models/response/product/tour/tour/get-tour-response';

@Component({
  selector: 'app-location-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './location-detail.component.html',
  styleUrl: './location-detail.component.css'
})
export class LocationDetailComponent {
  @Input() location: string | null = null;
  isExpanded = false; 
  locationId: string | null = null;
  locations: GetTourResponse[] = [];

  toggleContent(event: Event) {
    event.preventDefault(); 
    this.isExpanded = !this.isExpanded;
  }
  

  constructor(private route: ActivatedRoute,private tourService : TourService) { }

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
    this.tourService.getTour(id).subscribe(response => {
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
