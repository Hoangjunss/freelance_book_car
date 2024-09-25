import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GetHotelResponse } from '../../../../models/response/product/hotel/hotel/get-hotel-response';

@Component({
  selector: 'app-promotion',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './promotion.component.html',
  styleUrl: './promotion.component.css'
})
export class PromotionComponent {
  selectedImage: string = 'assets/img/DEFAULT/hotel-default.png';
  isDisplayDetails: boolean = false;
  hotel?: GetHotelResponse;
  data: GetHotelResponse[] = [];
  currentPage: number = 1;
  pageSize: number = 5;
  pagedData: GetHotelResponse[] = [];

  ngOnInit(): void {
    this.data = this.getData();
    this.updatePagedData();
    console.log(this.selectedImage);
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

  getData(): GetHotelResponse[] {
    return [
      { id: 1, name: 'Hotel California', contacInfo: '123-456-789', pricePerNight: 150, location: 'USA', active: true, rating: 4.5 },
      { id: 2, name: 'Grand Hotel', contacInfo: '987-654-321', pricePerNight: 200, location: 'France', active: true, rating: 4.2 },
      { id: 3, name: 'Royal Suite', contacInfo: '555-123-456', pricePerNight: 300, location: 'UK', active: false, rating: 4.8 },
      { id: 4, name: 'Beach Resort', contacInfo: '444-321-654', pricePerNight: 250, location: 'Australia', active: true, rating: 4.7 },
      { id: 5, name: 'Mountain Lodge', contacInfo: '222-789-123', pricePerNight: 180, location: 'Canada', active: false, rating: 4.1 },
      { id: 6, name: 'City Center Hotel', contacInfo: '111-222-333', pricePerNight: 220, location: 'Germany', active: true, rating: 4.3 },
    ];
  }

  displayDetailsHotel() {
    this.isDisplayDetails = true;
  }

  save() {
    if (this.hotel) {
      console.log('Saved:', {
        id: this.hotel.id,
        name: this.hotel.name,
        contacInfo: this.hotel.contacInfo,
        pricePerNight: this.hotel.pricePerNight,
        location: this.hotel.location,
        active: this.hotel.active,
        rating: this.hotel.rating
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
