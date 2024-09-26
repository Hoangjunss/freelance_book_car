import { TourService } from './../../../../services/product/tour/tour/tour.service';
import { CreateTourRequest } from './../../../../models/request/product/tour/tour/create-tour-request';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GetTourResponse } from '../../../../models/response/product/tour/tour/get-tour-response';
import { CreateTourResponse } from '../../../../models/response/product/tour/tour/create-tour-response';
import { NoDataFoundComponent } from "../../no-data-found/no-data-found.component";

@Component({
  selector: 'app-tour',
  standalone: true,
  imports: [CommonModule, FormsModule, NoDataFoundComponent],
  templateUrl: './tour.component.html',
  styleUrl: './tour.component.css'
})
export class TourComponent implements OnInit{
  createTourRequest: CreateTourRequest = new CreateTourRequest();
  createTourResponse: CreateTourResponse = new CreateTourResponse();
  imageUrl: string = 'assets/img/DEFAULT/tour-default.png';
  getALlTour: GetTourResponse[] = [];

  selectedImage: string = 'assets/img/DEFAULT/tour-default.png';
  isDisplayDetails: boolean = false;
  tour?: GetTourResponse;
  data: any[] = [];
  currentPage: number = 1;
  pageSize: number = 5;
  pagedData: any[] = [];

  constructor(private tourService:TourService){}

  ngOnInit(): void {
    this.getAllTour();
    this.updatePagedData();
    console.log(this.selectedImage);
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.updatePagedData();
  }

  updatePagedData() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.pagedData = this.getALlTour.slice(startIndex, endIndex);
  }

  get totalPages(): number {
    return Math.ceil(this.getALlTour.length / this.pageSize);
  }

  get pages(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }

  displayDetailsTour(){
    this.isDisplayDetails = true;
  }

  save() {
    
    if(this.tour != undefined){
      console.log('Saved:', {
      id: this.tour.id,
      name: this.tour.name,
      description: this.tour.description,
      startLocation: this.tour.startLocation,
      endLocation: this.tour.endLocation,
      isActive: this.tour.isActive,
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

  //On Submit
  onSubmit() {
    console.log(this.createTourRequest);
    if (!this.createTourRequest?.name || !this.createTourRequest?.startLocation || !this.createTourRequest?.endLocation || !this.createTourRequest?.description) {
      alert('Please fill in all required fields: Name, Location, Description');
      return;
    }

    const formData = new FormData();
    formData.append('name', this.createTourRequest.name || '');
    formData.append('description', this.createTourRequest.description || '');
    formData.append('startLocation', this.createTourRequest.startLocation || '');
    formData.append('endLocation', this.createTourRequest.endLocation || '');
    formData.append('isActive', this.createTourRequest.isActive ? 'true' : 'false');

      if(this.createTourRequest.isActive == undefined){
        this.createTourRequest.isActive = false;
      }
      this.tourService.createTour(formData).subscribe({
        next: (data) => {
          this.createTourResponse = data;
          if(this.createTourResponse){
            console.log('Tour created successfully:', this.createTourResponse);
            alert('Tour created successfully');
          }
        },
        error: (err) => {
          console.error('Error creating tour:', err.message);
        }
      });
  }



  //Get all Tour
  getAllTour(){
    this.tourService.getAllTour().subscribe({
      next: (data) => {
        this.getALlTour = data;
        console.log('All tours:', this.getALlTour);
      },
      error: (err) => {
        console.error('Error getting tours:', err.message);
      }
    })
  }




}
