import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { BookingService } from '../../../services/booking/booking.service';
import { GetBookingResponse } from '../../../models/response/booking/get-booking-response';
import { RouterLink } from '@angular/router';
import { GetBookingDetailResponse } from '../../../models/response/booking/get-booking-detail-response';
import { TourService } from '../../../services/product/tour/tour/tour.service';
import { HotelService } from '../../../services/product/hotel/hotel/hotel.service';
import { TicketService } from '../../../services/product/ticket/ticket/ticket.service';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule,RouterLink],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent implements OnInit {
  getBookingResponse: GetBookingResponse[] = [];
  products: any[] = [];
  getBookingDetailResponse: GetBookingDetailResponse[] = [];
  selectedOrder: any;

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

  constructor(private bookingService: BookingService,private tourService: TourService,private hotelService:HotelService,
    private ticketService: TicketService
  ) {}

  ngOnInit(): void {
    this.getBookingByUser();
  }

  viewOrderDetails(orderId: number): void {
    this.getBookingDetail(orderId);
    this.selectedOrder = this.getBookingResponse.find(order => order.id === orderId);
  }

  getBookingByUser(){
    const idUser = localStorage.getItem('idUser');

    this.bookingService.getHistoryBooking(parseInt(idUser)).subscribe({
      next: (data) =>{
        if(data && data.length > 0) {
          this.getBookingResponse = data;
          this.getBookingDetail(data[0].id);
        } else {
          this.products = [];
        }
      }
    })
  }

  getBookingDetail(id: number) {
    this.bookingService.getDetailBooking(id).subscribe({
      next: (response) => {
        if (response) {
          console.log(response);
          this.getBookingDetailResponse = response;
          this.products = this.getBookingDetailResponse.map(detail => ({
            idBookingDetail: detail.id,
            id: detail.idTour || detail.idHotel || detail.idTourism,
            name: detail.idTour ? 'Tour' : detail.idHotel ? 'Khách sạn' : 'Vé',
            price: detail.totalPrice,
            quantity: detail.quantity,
            image: 'https://via.placeholder.com/100',
            type: detail.idTour ? 'tour' : detail.idHotel ? 'hotel' : 'ticket'
          }));

          if (this.products.some(p => p.type === 'tour')) {
            this.products.filter(p => p.type === 'tour').forEach(p => {
              this.tourService.getTourById(p.id).subscribe({
                next: (response) => {
                  console.log('Tour:', response);
                  p.startLocation = response.startLocation;
                  p.endLocation = response.endLocation;
                  p.image = response.image;
                  p.name = response.name;
                },
                error: (error) => {
                  console.log("Error:", error);
                }
              });
            });
          }

          if (this.products.some(p => p.type === 'hotel')) {
            this.products.filter(p => p.type === 'hotel').forEach(p => {
              this.hotelService.getHotelDetailById(p.id).subscribe({
                next: (response) => {
                  console.log('Hotel:', response);
                  p.name = response.name;
                  p.image = response.image;
                  p.location = response.location;


                },
                error: (error) => {
                  console.log("Error:", error);
                }
              });
            });
          }

          if (this.products.some(p => p.type === 'ticket')) {
            this.products.filter(p => p.type === 'ticket').forEach(p => {
              this.ticketService.getTicket(p.id).subscribe({
                next: (response) => {
                  console.log('Ticket:', response);
                  p.name = response.startDate;
                },
                error: (error) => {
                  console.log("Error:", error);
                }
              });
            });
          }
        } else {
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

}
