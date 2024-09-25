import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GetTicketResponse } from '../../../../models/response/product/ticket/ticket/get-ticket-response';
import { GetTourismResponse } from '../../../../models/response/product/ticket/tourism/get-tourism-response';
import { GetTourScheduleResponse } from '../../../../models/response/product/tour/tour-schedule/get-tour-schedule-response';
import { GetTourResponse } from '../../../../models/response/product/tour/tour/get-tour-response';

@Component({
  selector: 'app-ticket',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css']
})
export class TicketComponent implements OnInit {
  isDisplayDetails: boolean = false;
  isUpdateSchedule: boolean = false; 
  selectedTourId: number | null | undefined = null; // Chấp nhận undefined
  isEditMode: boolean = false;

  selectedSchedule: GetTourScheduleResponse = {};

  tourisms: GetTourismResponse[] = [
    { id: 1, name: 'Tourism 1', location: 'Location 1', description: 'A beautiful place to visit', rating: 4.5 },
    { id: 2, name: 'Tourism 2', location: 'Location 2', description: 'A serene getaway', rating: 4.0 },
    { id: 3, name: 'Tourism 3', location: 'Location 3', description: 'An adventure for thrill seekers', rating: 4.8 },
    { id: 4, name: 'Tourism 4', location: 'Location 4', description: 'Rich in culture and history', rating: 4.2 },
    { id: 5, name: 'Tourism 5', location: 'Location 5', description: 'Perfect for family vacations', rating: 4.7 }
  ];

  tickets: GetTicketResponse[] = [
    { id: 1, startDate: new Date('2024-09-01'), tourPrice: 100, idTourism: 1 },
    { id: 2, startDate: new Date('2024-09-05'), tourPrice: 150, idTourism: 1 },
    { id: 3, startDate: new Date('2024-09-10'), tourPrice: 200, idTourism: 2 },
    { id: 4, startDate: new Date('2024-09-15'), tourPrice: 250, idTourism: 3 },
    { id: 5, startDate: new Date('2024-09-20'), tourPrice: 300, idTourism: 4 },
    { id: 6, startDate: new Date('2024-09-25'), tourPrice: 350, idTourism: 5 }
  ];

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
  pagedTours: GetTicketResponse[] = [];
  totalPages: number = Math.ceil(this.tours.length / this.pageSize);
  pages: number[] = Array.from({ length: this.totalPages }, (_, i) => i + 1);

  currentPage = 1;
  itemsPerPage = 4;

  get paginatedTours() {
    const start = (this.currentPage - 1) * this.itemsPerPage;
    return this.tourisms.slice(start, start + this.itemsPerPage);
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
    this.updatePagedTours();
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
