import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GetTourismResponse } from '../../../../models/response/product/ticket/tourism/get-tourism-response';

@Component({
  selector: 'app-tourism',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tourism.component.html',
  styleUrls: ['./tourism.component.css']
})
export class TourismComponent {
  selectedImage: string = 'assets/img/DEFAULT/tour-default.png';
  isDisplayDetails: boolean = false;
  tour?: GetTourismResponse;
  data: GetTourismResponse[] = [];
  currentPage: number = 1;
  pageSize: number = 5;
  pagedData: GetTourismResponse[] = [];

  ngOnInit(): void {
    this.data = this.getData();
    this.updatePagedData();
  }

  updatePagedData() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.pagedData = this.data.slice(startIndex, endIndex);
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.updatePagedData();
  }

  get totalPages(): number {
    return Math.ceil(this.data.length / this.pageSize);
  }

  get pages(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }

  getData(): GetTourismResponse[] {
    return [
      { id: 1, name: 'Tour A', location: 'Location A', description: 'Description A', rating: 4.5 },
      { id: 2, name: 'Tour B', location: 'Location B', description: 'Description B', rating: 4.0 },
      // Add more mock data
    ];
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
}
