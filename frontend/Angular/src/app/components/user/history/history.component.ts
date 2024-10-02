import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { BookingService } from '../../../services/booking/booking.service';
import { GetBookingResponse } from '../../../models/response/booking/get-booking-response';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent implements OnInit {
  getBookingResponse: GetBookingResponse[] = [];

  orders = [
    {
        id: 1,
        date: '25/09/2024',
        type: 'Đặt Khách Sạn',
        hotelName: 'Khách sạn Grand Palace',
        checkIn: '01/10/2024',
        checkOut: '05/10/2024',
        total: 5000000
    },
    {
        id: 2,
        date: '20/09/2024',
        type: 'Đặt Xe',
        carType: 'Xe hơi',
        pickupLocation: 'Sân bay Nội Bài',
        dropOffLocation: 'Khách sạn Grand Palace',
        total: 1500000
    },
    {
        id: 3,
        date: '15/09/2024',
        type: 'Đặt Vé',
        event: 'Hòa nhạc BTS',
        venue: 'SVĐ Mỹ Đình',
        dateEvent: '10/10/2024',
        total: 3000000
    }
];

  constructor(private bookingService: BookingService) {}

  ngOnInit(): void {
    this.getBookingByUser();
  }

  viewOrderDetails(orderId: number): void {
    alert(`Chi tiết đơn hàng ${orderId}`);
  }

  getBookingByUser(){
    const idUser = localStorage.getItem('idUser');

    this.bookingService.getHistoryBooking(parseInt(idUser)).subscribe({
      next: (data) =>{
        if(data){
          this.getBookingResponse = data;
        }
      }
    })
  }

}
