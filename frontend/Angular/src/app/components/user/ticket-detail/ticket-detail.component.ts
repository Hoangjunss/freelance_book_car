import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { GetTourismDetailResponse } from '../../../models/response/product/ticket/tourism/get-tourism-detail-response';
import { ActivatedRoute } from '@angular/router';
import { TourismService } from '../../../services/product/ticket/tourism/tourism.service';

@Component({
  selector: 'app-ticket-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ticket-detail.component.html',
  styleUrl: './ticket-detail.component.css'
})
export class TicketDetailComponent {

  @Input() location: string | null = null;
  isExpanded = false; 
  locationId: string | null = null;
  locations: GetTourismDetailResponse[] = [];

  toggleContent(event: Event) {
    event.preventDefault(); 
    this.isExpanded = !this.isExpanded;
  }
  

  constructor(private route: ActivatedRoute,private tourismService : TourismService) { }

  ngOnInit(): void {
    console.log("tourismDetailComponent initialized");
    this.route.paramMap.subscribe(params => {
      this.locationId = params.get('id');
      console.log("tourism "+this.locationId);
      if (this.locationId) {
        this.getTourismDetailById(parseInt(this.locationId));
      }
    });
  }

  getTourismDetailById(id: number) {
    this.tourismService.getTourismDetailById(id).subscribe(response => {
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
