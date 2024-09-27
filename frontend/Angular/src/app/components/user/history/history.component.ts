import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent implements OnInit {
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

  constructor() {}

  ngOnInit(): void {}

  viewOrderDetails(orderId: number): void {
    // Ở đây có thể chuyển hướng hoặc mở modal hiển thị chi tiết đơn hàng
    alert(`Chi tiết đơn hàng ${orderId}`);
  }
}
