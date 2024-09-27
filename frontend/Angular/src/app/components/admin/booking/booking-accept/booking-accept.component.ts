import { Component, OnInit } from '@angular/core';
import { GetBookingResponse } from '../../../../models/response/booking/get-booking-response';
import { BookingService } from '../../../../services/booking/booking.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NoDataFoundComponent } from "../../no-data-found/no-data-found.component";

@Component({
  selector: 'app-booking-accept',
  standalone: true,
  imports: [CommonModule, FormsModule, NoDataFoundComponent],
  templateUrl: './booking-accept.component.html',
  styleUrl: './booking-accept.component.css'
})
export class BookingAcceptComponent implements OnInit {

  type: string = 'ACCEPT';

  getBookingResponse: GetBookingResponse[] = [];


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
