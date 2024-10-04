import { Component } from '@angular/core';
import { GetBookingResponse } from '../../../../models/response/booking/get-booking-response';
import { BookingService } from '../../../../services/booking/booking.service';
import { NoDataFoundComponent } from "../../no-data-found/no-data-found.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UpdateBookingResponse } from '../../../../models/response/booking/update-booking-response';
import { GetUserInfoResponse } from '../../../../models/response/user/user-info/get-user-info-response';
import { GetUserJoinResponse } from '../../../../models/response/user/user-join/get-user-join-response';
import { GetBookingDetailResponse } from '../../../../models/response/booking/get-booking-detail-response';
import { TourService } from '../../../../services/product/tour/tour/tour.service';
import { HotelService } from '../../../../services/product/hotel/hotel/hotel.service';
import { TicketService } from '../../../../services/product/ticket/ticket/ticket.service';
import { TourScheduleService } from '../../../../services/product/tour/tour-schedule/tour-schedule.service';
import { TourismService } from '../../../../services/product/ticket/tourism/tourism.service';
import { HotelbookingService } from '../../../../services/product/hotel/hotelbooking/hotelbooking.service';

@Component({
  selector: 'app-booking-pending',
  standalone: true,
  imports: [CommonModule, FormsModule, NoDataFoundComponent],
  templateUrl: './booking-pending.component.html',
  styleUrl: './booking-pending.component.css'
})
export class BookingPendingComponent {
  getBookingResponse: GetBookingResponse[] = [];
  updateBookingResponse: UpdateBookingResponse = new UpdateBookingResponse();

  type: string = 'PENDING';

  getUserInfo: GetUserInfoResponse[] = [];
  getUserJoin: GetUserJoinResponse[] = [];

  isUserInfo = false;
  isUserJoin = false;

  currentPage: number = 1;
  pageSize: number = 5;
  pagedData: any[] = [];

  getBookingResponse1: GetBookingResponse[] = [];
  products: any[] = [];
  getBookingDetailResponse: GetBookingDetailResponse[] = [];
  selectedOrder: any;

  constructor(private bookingService: BookingService,
    private tourService: TourService,
    private hotelService: HotelService,
    private ticketService: TicketService,
    private tourScheduleService: TourScheduleService,
    private tourismService: TourismService,
    private hotelBookingService: HotelbookingService,
  ){}

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

  setTypeBooking(id?: number, type?: string){
    console.log("Click");
    console.log(id+type);
    if(id && type){
      this.bookingService.setBookingType(type, id).subscribe({
      next: (data) => {
        alert('Thành công');
        this.updateBookingResponse = data;
        window.location.reload();
        }
      })
    }
  }

  getUserInfoBooking(idBooking: number){
    this.bookingService.getUserInfo(idBooking).subscribe({
      next: (data)=>{
        if(data){
          this.getUserInfo = data;
        }
      }
    })
  }

  openInfo(idBooking:number){
    this.getUserInfoBooking(idBooking);
    this.isUserInfo = true;
  }

  closeInfo(){
    this.isUserInfo = false;
  }

  openJoin(idBooking:number){
    this.getUserJoinBooking(idBooking);
    this.isUserJoin = true;
  }

  closeJoin(){
    this.isUserJoin = false;
  }
  

  getUserJoinBooking(idBooking: number){
    this.bookingService.getUserJoin(idBooking).subscribe({
      next: (data)=>{
        if(data){
          this.getUserJoin = data;
        }
      }
    })
  }

  viewOrderDetails(orderId: number): void {
    this.getBookingDetail(orderId);
    this.selectedOrder = this.getBookingResponse.find(order => order.id === orderId);
  }

  getBookingDetail(id: number) {
    this.bookingService.getDetailBooking(id).subscribe({
      next: (response) => {
        if (response) {
          console.log(response);
          this.getBookingDetailResponse = response;
          this.products = this.getBookingDetailResponse.map(detail => ({
            idBookingDetail: detail.id,
            id: detail.idTour || detail.idHotel || detail.idTicket,
            name: detail.idTour ? 'Tour' : detail.idHotel ? 'Khách sạn' : 'Vé',
            price: detail.totalPrice,
            quantity: detail.quantity,
            image: 'https://via.placeholder.com/100',
            type: detail.idTour ? 'tour' : detail.idHotel ? 'hotel' : 'ticket',
            schedule: null,
            startDate: null,
            endDate: null,
            description: null,
            venue: null,
            dateEvent: null,
          }));

          if (this.products.some(p => p.type === 'tour')) {
            this.products.filter(p => p.type === 'tour').forEach(p => {
              this.tourService.getTourById(p.id).subscribe({
                next: (tourResponse) => {
                  p.startLocation = tourResponse.startLocation;
                  p.endLocation = tourResponse.endLocation;
                  p.image = tourResponse.image;
                  p.name = tourResponse.name;
                  this.tourScheduleService.getSchedule(p.id).subscribe({
                    next: (scheduleResponse) => {
                      p.schedule = scheduleResponse;
                      console.log('Schedule:', p.schedule);
                    },
                    error: (error: any) => {
                      console.log("Error:", error);
                    }
                  });
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
                next: (hotelResponse) => {
                  p.name = hotelResponse.name;
                  p.image = hotelResponse.image;
                  p.location = hotelResponse.location;
                  this.hotelBookingService.getBooking(p.id).subscribe({
                    next: (hotelBookingResponse) => {
                      console.log('Hotel list:', hotelBookingResponse);
                      p.startDate = hotelBookingResponse.startDate;
                      p.endDate = hotelBookingResponse.endDate;
                    },
                    error: (error) => {
                      console.log("Error:", error);
                    }
                  });


                },
                error: (error) => {
                  console.log("Error:", error);
                }
              });
            });
          }

          if (this.products.some(p => p.type === 'ticket')) {
            this.products.filter(p => p.type === 'ticket').forEach(p => {
              console.log('Ticket ID:', p.id);
              this.ticketService.getTicket(p.id).subscribe({
                next: (ticketResponse) => {
                  p.dateEvent = ticketResponse.startDate;
                  this.tourismService.getTour(p.id).subscribe({
                    next: (scheduleTicketResponse) => {
                      p.image = scheduleTicketResponse.image;
                      p.description = scheduleTicketResponse.description;
                      p.venue = scheduleTicketResponse.location;
                      p.name = scheduleTicketResponse.name;
                      console.log('Ticket:', scheduleTicketResponse);
                    },
                    error: (error) => {
                      console.log("Error:", error);
                    }
                  });
                },
                error: (error) => {
                  console.log("Error:", error);
                }
              });
            });
          }


        }
      }
    });
  }

}
