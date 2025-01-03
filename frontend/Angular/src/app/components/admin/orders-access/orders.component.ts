import { Component } from '@angular/core';
import { GetBookingResponse } from '../../../models/response/booking/get-booking-response';
import { BookingService } from '../../../services/booking/booking.service';
import { UpdateBookingResponse } from '../../../models/response/booking/update-booking-response';
import { GetUserInfoResponse } from '../../../models/response/user/user-info/get-user-info-response';
import { GetUserJoinResponse } from '../../../models/response/user/user-join/get-user-join-response';
import { GetBookingDetailResponse } from '../../../models/response/booking/get-booking-detail-response';
import { TourService } from '../../../services/product/tour/tour/tour.service';
import { HotelService } from '../../../services/product/hotel/hotel/hotel.service';
import { TicketService } from '../../../services/product/ticket/ticket/ticket.service';
import { TourScheduleService } from '../../../services/product/tour/tour-schedule/tour-schedule.service';
import { TourismService } from '../../../services/product/ticket/tourism/tourism.service';
import { HotelbookingService } from '../../../services/product/hotel/hotelbooking/hotelbooking.service';
import { NoDataFoundComponent } from "../no-data-found/no-data-found.component";
import { OrdersService } from '../../../services/admin/orders/orders.service';
import { OrderDTO } from '../../../models/AdminSupplier/response/orders/orders';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [CommonModule, FormsModule, NoDataFoundComponent],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersAccessComponent {
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
  
    getBookingResponse1: OrderDTO;
    products: any[] = [];
    getBookingDetailResponse: GetBookingDetailResponse[] = [];
    selectedOrder: any;
  
    constructor(private bookingService: OrdersService,
      private tourService: TourService,
      private hotelService: HotelService,
      private ticketService: TicketService,
      private tourScheduleService: TourScheduleService,
      private tourismService: TourismService,
      private hotelBookingService: HotelbookingService,
    ){}
  
    ngOnInit(): void {
      this.getAll();
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
      this.bookingService.getOrderOfSupplierAccess().subscribe({
        next: (data) => {
          this.getBookingResponse = data;
          this.updatePagedData();
        }
      })
    }
  
    getAllOrders(){
      this.bookingService.getOrderOfSupplierAccess().subscribe({
        next: (data) => {
          this.getBookingResponse = data;
          this.updatePagedData();
        }
      })
    }
  
    setTypeBooking(id?: number, idStatus?: number){
        this.bookingService.updateStatusOrder(id, idStatus).subscribe({
        next: (data) => {
          alert('Thành công');
          this.updateBookingResponse = data;
          window.location.reload();
          }
        })
  }
  
    getUserInfoBooking(idBooking: number){
      /* this.bookingService.getUserInfo(idBooking).subscribe({
        next: (data)=>{
          if(data){
            this.getUserInfo = data;
          }
        }
      }) */
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
      /* this.bookingService.getUserJoin(idBooking).subscribe({
        next: (data)=>{
          if(data){
            this.getUserJoin = data;
          }
        }
      }) */
    }
  
    viewOrderDetails(orderId: number): void {
      this.selectedOrder = this.getBookingDetail(orderId);
    }
  
    getBookingDetail(id: number) {
      this.bookingService.getOrderById(id).subscribe({
        next: (data)=>{
          if(data){
            this.selectedOrder = data;
          }
        }
      })
    }
  
}
