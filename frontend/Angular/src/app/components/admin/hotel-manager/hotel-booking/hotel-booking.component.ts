import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GetTourScheduleResponse } from '../../../../models/response/product/tour/tour-schedule/get-tour-schedule-response';
import { GetTourResponse } from '../../../../models/response/product/tour/tour/get-tour-response';

@Component({
  selector: 'app-hotel-booking',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './hotel-booking.component.html',
  styleUrl: './hotel-booking.component.css'
})
export class HotelBookingComponent implements OnInit{
  isDisplayDetails: boolean = false;
  isUpdateSchedule: boolean = false; 
  selectedTourId: number | null | undefined = null; // Chấp nhận undefined
  isEditMode: boolean = false;

  selectedSchedule: GetTourScheduleResponse = {};

  tour: GetTourResponse = {}; 
  tourSchedules: GetTourScheduleResponse[] = [
    { id: 1, timeStartTour: new Date(), quantity: 10, priceTour: 100, idTourScheduleStatus: 1 },
    { id: 2, timeStartTour: new Date(), quantity: 5, priceTour: 150, idTourScheduleStatus: 1 },
    { id: 1, timeStartTour: new Date(), quantity: 10, priceTour: 100, idTourScheduleStatus: 1 },
    { id: 2, timeStartTour: new Date(), quantity: 5, priceTour: 150, idTourScheduleStatus: 1 },
    { id: 1, timeStartTour: new Date(), quantity: 10, priceTour: 100, idTourScheduleStatus: 1 },
    { id: 2, timeStartTour: new Date(), quantity: 5, priceTour: 150, idTourScheduleStatus: 1 },
    { id: 1, timeStartTour: new Date(), quantity: 10, priceTour: 100, idTourScheduleStatus: 1 },
    { id: 2, timeStartTour: new Date(), quantity: 5, priceTour: 150, idTourScheduleStatus: 1 },
    { id: 1, timeStartTour: new Date(), quantity: 10, priceTour: 100, idTourScheduleStatus: 1 },
    { id: 2, timeStartTour: new Date(), quantity: 5, priceTour: 150, idTourScheduleStatus: 1 },

  ];
  tours: GetTourScheduleResponse[] = [
    { id: 1, timeStartTour: new Date(), timeEndTour: new Date(), idTour: 101, quantity: 2, priceTour: 500, idTourScheduleStatus: 1 },
    { id: 2, timeStartTour: new Date(), timeEndTour: new Date(), idTour: 102, quantity: 4, priceTour: 700, idTourScheduleStatus: 2 },
    { id: 3, timeStartTour: new Date(), timeEndTour: new Date(), idTour: 103, quantity: 1, priceTour: 300, idTourScheduleStatus: 1 },
    { id: 4, timeStartTour: new Date(), timeEndTour: new Date(), idTour: 104, quantity: 3, priceTour: 400, idTourScheduleStatus: 2 },
    { id: 5, timeStartTour: new Date(), timeEndTour: new Date(), idTour: 105, quantity: 2, priceTour: 600, idTourScheduleStatus: 1 },
    { id: 6, timeStartTour: new Date(), timeEndTour: new Date(), idTour: 106, quantity: 5, priceTour: 800, idTourScheduleStatus: 2 },
  ];

  // Pagination variables
  currentPageSchedule: number = 1;
  pageSize: number = 3;
  pagedTours: GetTourScheduleResponse[] = [];
  totalPages: number = Math.ceil(this.tours.length / this.pageSize);
  pages: number[] = Array.from({ length: this.totalPages }, (_, i) => i + 1);

  currentPage = 1;
  itemsPerPage = 4;

  get paginatedTours() {
    const start = (this.currentPage - 1) * this.itemsPerPage;
    return this.tours.slice(start, start + this.itemsPerPage);
  }

  nextPage() {
    if (this.currentPage < Math.ceil(this.tours.length / this.itemsPerPage)) {
      this.currentPage++;
    }
  }

  addNewSchedule(): void {
    this.selectedSchedule = {};  // Reset the form
    this.selectedTourId = undefined;  // Clear selected tour
    this.isUpdateSchedule = true;
    this.isEditMode = false;  // Mark as add mode
  }

  updateSchedule(schedule?: GetTourScheduleResponse) {
    if (schedule != undefined) {
      this.selectedSchedule = { ...schedule }; // Gán lịch trình được chọn
      this.selectedTourId = schedule.idTour; // Gán ID tour tương ứng
    } else {
      this.selectedTourId = undefined;
    }
    this.isUpdateSchedule = true; // Đóng modal cập nhật
    this.isEditMode = true;  // Mark as add mode

  }
  

  cancelUpdate() {
    this.isUpdateSchedule = false; // Đóng modal cập nhật
  }

  saveUpdate() {
    // Logic lưu dữ liệu cho lịch trình
    // Cập nhật danh sách tourSchedules nếu cần
    this.isUpdateSchedule = false; // Đóng modal sau khi lưu
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  viewDetails(id: number) {
    // Logic xem chi tiết tour
    this.isDisplayDetails = true;
  }

  ngOnInit(): void {
  }

  cancel() {
    this.isDisplayDetails = false; // Đóng modal
  }

  save() {
    // Logic lưu dữ liệu
  }

  viewSchedule(id: number) {
    console.log(`Viewing schedule with ID: ${id}`);
  }

  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPageSchedule = page;
      this.updatePagedTours();
    }
  }
  updatePagedTours(): void {
    const start = (this.currentPageSchedule - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.pagedTours = this.tours.slice(start, end);
  }
}
