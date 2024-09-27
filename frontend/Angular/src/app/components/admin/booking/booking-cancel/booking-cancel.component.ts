import { Component } from '@angular/core';
import { GetBookingResponse } from '../../../../models/response/booking/get-booking-response';
import { BookingService } from '../../../../services/booking/booking.service';
import { NoDataFoundComponent } from "../../no-data-found/no-data-found.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-booking-cancel',
  standalone: true,
  imports: [CommonModule, FormsModule, NoDataFoundComponent],
  templateUrl: './booking-cancel.component.html',
  styleUrl: './booking-cancel.component.css'
})
export class BookingCancelComponent {
  getBookingResponse: GetBookingResponse[] = [];

  type: string = 'CANCEL';

  currentPage: number = 1;
  pageSize: number = 5;
  pagedData: GetBookingResponse[] = [];

  constructor(private bookingService: BookingService){}

  ngOnInit(): void {
    this.getByType(this.type);
    this.updatePagedData();
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.updatePagedData();
  }

  updatePagedData() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.pagedData = this.getBookingResponse.slice(startIndex, endIndex);
  }

  get totalPages(): number {
    return Math.ceil(this.getBookingResponse.length / this.pageSize);
  }

  get pages(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }

  getAll(){
    this.bookingService.getAll().subscribe({
      next: (data) => {
        this.getBookingResponse = data;
        this.updatePagedData();
      }
    })
  }
  getByType(type: string){
    this.bookingService.getByType(type).subscribe({
      next: (data) => {
        this.getBookingResponse = data;
        this.updatePagedData();
      }
    })
  }
}
