import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GetTourismResponse } from '../../../../models/response/product/ticket/tourism/get-tourism-response';
import { CreateTourismRequest } from '../../../../models/request/product/ticket/tourism/create-tourism-request';
import { CreateTourismResponse } from '../../../../models/response/product/ticket/tourism/create-tourism-response';
import { TourismService } from '../../../../services/product/ticket/tourism/tourism.service';
import { NoDataFoundComponent } from "../../no-data-found/no-data-found.component";

@Component({
  selector: 'app-tourism',
  standalone: true,
  imports: [CommonModule, FormsModule, NoDataFoundComponent],
  templateUrl: './tourism.component.html',
  styleUrls: ['./tourism.component.css']
})
export class TourismComponent {
  createTourismRequest: CreateTourismRequest = new CreateTourismRequest();
  createTourismResponse: CreateTourismResponse = new CreateTourismResponse();
  getTourismResponse: GetTourismResponse[] = [];

  selectedImage: string = 'assets/img/DEFAULT/tour-default.png';
  isDisplayDetails: boolean = false;
  tour?: GetTourismResponse;
  currentPage: number = 1;
  pageSize: number = 5;
  pagedData: GetTourismResponse[] = [];

  constructor(private tourismService: TourismService){}

  ngOnInit(): void {
    this.getAllTourisms();
    this.updatePagedData();
  }

  updatePagedData() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.pagedData = this.getTourismResponse.slice(startIndex, endIndex);
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.updatePagedData();
  }

  get totalPages(): number {
    return Math.ceil(this.getTourismResponse.length / this.pageSize);
  }

  get pages(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }

  displayDetailsTour() {
    this.isDisplayDetails = true;
  }

  save() {
    if (this.tour) {
      console.log('Saved:', {
        id: this.tour.id,
        name: this.tour.name,
        location: this.tour.location,
        description: this.tour.description,
        rating: this.tour.rating,
      });
    }
  }

  onImageSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.selectedImage = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  cancel() {
    this.isDisplayDetails = false;
    console.log('Cancelled');
  }

  onSubmit() {
    console.log('Tour data:', this.createTourismRequest);
  
    if (!this.createTourismRequest?.name || !this.createTourismRequest?.location || !this.createTourismRequest?.description) {
      alert('Please fill in all required fields: Name, Location, Description');
      return;
    }
  
    this.tourismService.createTour(this.createTourismRequest).subscribe({
      next: (data) => {
        this.createTourismResponse = data;
        if(this.createTourismResponse){
          console.log('Tour created successfully:', data);
          alert('Tour created successfully');
        }
      },
      error: (err) => {
        console.error('Error creating tour:', err.message);
        alert(`Error creating tour: ${err.message}`);
      }
    });
  }

  getAllTourisms(){
    this.tourismService.getAllTourism().subscribe({
      next: (data) =>{
        this.getTourismResponse = data;
      },
      error: (err) => {
        console.error('Error get all tourism:', err.message);
        alert(`Error creating tourism: ${err.message}`);
      }
    })
  }

}
