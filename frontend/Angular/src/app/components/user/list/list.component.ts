import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { GetTourResponse } from '../../../models/response/product/tour/tour/get-tour-response';
import { TourService } from '../../../services/product/tour/tour/tour.service';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent implements OnInit {

  tours: GetTourResponse[] = [];
  location: string;
  groupedTours: { [key: string]: GetTourResponse[] } = {};
  titleParts = ['Di chuyển', 'Việt Nam'];
  backgroundImage: string;

  navButtons: any[] = [];

  constructor(private tourService: TourService, private title: Title, private route: ActivatedRoute, private router: Router) {
    this.title.setTitle("Danh sách địa điểm");
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.location = params.get('location') || '';
      this.getAllTour();
    });
    this.backgroundImage = 'http://res.cloudinary.com/dgts7tmnb/image/upload/v1727963818/csdch7mi171qddwizcxq.png';
  }

  getAllTour() {
    this.tourService.getAllTour().subscribe({
      next: (response) => {
        if (response) {
          this.tours = response.filter(tour => tour.isActive === true);  
          console.log(this.tours);
          this.groupToursByStartLocation();
          this.navButtons = this.getAllTourKeys(); 
        } else {
          this.tours = [];
        }
      },
      error(err) {
        console.error('Error fetching tours:', err);
      },
    });
  }

  private groupToursByStartLocation(): void {
    this.groupedTours = this.tours.reduce((groups, tour) => {
      const startLocation = tour.startLocation || 'Unknown';
      if (!groups[startLocation]) {
        groups[startLocation] = [];
      }
      groups[startLocation].push(tour);
      return groups;
    }, {} as { [key: string]: GetTourResponse[] });
  }

  getAllTourKeys(): string[] {
    return Object.keys(this.groupedTours);
  }

  navigateToLocation(location: string) {
    this.router.navigate(['/list', location]);
  }
  goToLocationDetails(locationId?: number) {
    const path = `/location-details/${locationId}`;
    this.router.navigate([path]);
  }
}
