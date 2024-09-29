import { Component, OnInit } from '@angular/core';
import { GetTourScheduleResponse } from '../../../../models/response/product/tour/tour-schedule/get-tour-schedule-response';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GetTourResponse } from '../../../../models/response/product/tour/tour/get-tour-response';
import { TourService } from '../../../../services/product/tour/tour/tour.service';
import { TourScheduleService } from '../../../../services/product/tour/tour-schedule/tour-schedule.service';
import { CreateTourScheduleRequest } from '../../../../models/request/product/tour/tour-schedule/create-tour-schedule-request';
import { UpdateTourScheduleRequest } from '../../../../models/request/product/tour/tour-schedule/update-tour-schedule-request';
import { CreateTourScheduleResponse } from '../../../../models/response/product/tour/tour-schedule/create-tour-schedule-response';
import { UpdateTourScheduleResponse } from '../../../../models/response/product/tour/tour-schedule/update-tour-schedule-response';

@Component({
  selector: 'app-tour-schedule',
  standalone: true,
  imports: [CommonModule, FormsModule], // Chỉ cần import FormsModule
  templateUrl: './tour-schedule.component.html',
  styleUrls: ['./tour-schedule.component.css']
})
export class TourScheduleComponent implements OnInit {
  createTourScheduleRequest: CreateTourScheduleRequest = new CreateTourScheduleRequest();
  updateTourScheduleRequest: UpdateTourScheduleRequest = new UpdateTourScheduleRequest();
  createTourScheduleResponse: CreateTourScheduleResponse = new CreateTourScheduleResponse();
  updateTourScheduleResponse: UpdateTourScheduleResponse = new UpdateTourScheduleResponse();

  tourDetail: GetTourResponse = new GetTourResponse();
  tourSchedule: GetTourScheduleResponse = new GetTourScheduleResponse();

  getTourResponse: GetTourResponse[] = [];
  getTourScheduleResponse: GetTourScheduleResponse[] = [];
  getTourScheduleByIdTour: GetTourScheduleResponse[] = [];

  timeStartTour: Date = new Date();
  timeEndTour: Date = new Date();

  tourSelectedId: number=0;

  tourSelected: GetTourResponse = new GetTourResponse();

  constructor(private tourService:TourService, private tourScheduleService: TourScheduleService){}


  ngOnInit(): void {
    this.getAllTour();
    this.getAllTourSchedule();
    this.updateDisplayedPages();
  }

  getAllTour(){
    this.tourService.getAllTour().subscribe({
      next: (data)=> {
          if(data){
            this.getTourResponse = data;
          }
      },
    })
  }

  getTourScheduleByidTour(id:number){
    this.tourScheduleService.getTourScheduleByidTour(id).subscribe({
      next: (data) =>{
        if(data){
          this.getTourScheduleByIdTour = data;
        }
      }
    })
  }

  getAllTourSchedule(){
    this.tourScheduleService.getAllTourSchedule().subscribe({
      next: (data) =>{
        if(data){
          this.getTourScheduleResponse = data;
          this.updatePagedTours();
        }
      }
    })
  }


  isDisplayDetails: boolean = false;
  isUpdateSchedule: boolean = false;
  isCreateSchedule: boolean = false; 
  selectedTourId: number | null | undefined = null; // Chấp nhận undefined
  isEditMode: boolean = false;

  selectedSchedule: GetTourScheduleResponse = {};

  tour: GetTourResponse = {}; 

  // Pagination variables
  currentPageSchedule: number = 1;
  pageSize: number = 5;
  pagedTours: GetTourScheduleResponse[] = [];
  totalPages: number = Math.ceil(this.getTourScheduleResponse.length / this.pageSize);
  pages: number[] = Array.from({ length: this.totalPages }, (_, i) => i + 1);


  currentPage = 1;
  itemsPerPage = 4;


  get paginatedTours() {
    const start = (this.currentPage - 1) * this.itemsPerPage;
    return this.getTourResponse.slice(start, start + this.itemsPerPage);
  }

  nextPage() {
    if (this.currentPage < Math.ceil(this.getTourResponse.length / this.itemsPerPage)) {
      this.currentPage++;
    }
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }


  addNewSchedule(): void {
    this.createTourScheduleRequest.idTour=0;
    this.isCreateSchedule = true;
  }
  onCreate() {
    console.log(this.createTourScheduleRequest);
    console.log(this.timeStartTour);
    console.log(this.timeEndTour);

    
    
    // Chuyển đổi các giá trị chuỗi thành đối tượng Date
    const startDate = new Date(this.timeStartTour); // Giả sử timeStartTour đã được định dạng đúng
    const endDate = new Date(this.timeEndTour); // Giả sử timeEndTour đã được định dạng đúng
    // Chuyển đổi startDate thành chuỗi theo định dạng yyyy-MM-ddTHH:mm:ss
    let formattedStartDate = startDate.toISOString().slice(0, 19); // loại bỏ phần "Z" để chỉ giữ lại yyyy-MM-ddTHH:mm:ss

    // Tương tự cho endDate nếu cần
    let formattedEndDate = endDate.toISOString().slice(0, 19); // loại bỏ phần "Z"
    let isSuccess = false;

    if (startDate < new Date()) {
        alert("Ngày bắt đầu không được nhỏ hơn ngày hiện tại.");
        return; // Dừng lại nếu ngày bắt đầu không hợp lệ
    }

    // Kiểm tra xem ngày bắt đầu có nhỏ hơn hoặc bằng ngày kết thúc không
    if (startDate <= endDate) {
        while (startDate <= endDate) {
            const formData = new FormData();
            formData.append('idTour', this.createTourScheduleRequest.idTour?.toString() ?? '');
            formData.append('priceTour', this.createTourScheduleRequest.priceTour?.toString() ?? '');
            formData.append('quantity', this.createTourScheduleRequest.quantity?.toString() ?? '');
            formData.append('timeStartTour', formattedStartDate); // Chuyển đổi ngày bắt đầu sang ISO string

            this.tourScheduleService.createSchedule(formData).subscribe({
                next: (data) => {
                    if (data) {
                        this.createTourScheduleResponse = data;
                        isSuccess = true;
                    } else {
                        isSuccess = false;
                    }
                },
                error: (error) => {
                    console.error("Error creating schedule:", error);
                }
            });

            startDate.setDate(startDate.getDate() + 1);
            formattedStartDate = startDate.toISOString().slice(0, 19);
        }
        
        if (isSuccess) {
            alert('Create TourSchedule is success');
        } else {
            alert('False');
        }
    } else {
        console.error('Start date must be less than or equal to end date.');
    }
}




  closeCreate(){
    this.isCreateSchedule = false;
  }

  updateSchedule(schedule?: GetTourScheduleResponse, tourDetail?: GetTourResponse) {
    if (schedule != undefined && tourDetail != undefined) {
      this.updateTourScheduleRequest = schedule;
      this.tourSelected = tourDetail;
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
    this.updateTourScheduleRequest;
    if(this.updateTourScheduleRequest){
      const formData = new FormData();
      formData.append('id', this.updateTourScheduleRequest.id?.toString() ?? '');
      formData.append('idTour', this.updateTourScheduleRequest.idTour?.toString() ?? '');
      formData.append('priceTour', this.updateTourScheduleRequest.priceTour?.toString() ?? '');
      formData.append('quantity', this.updateTourScheduleRequest.quantity?.toString() ?? '');
      formData.append('timeStartTour', this.updateTourScheduleRequest.timeStartTour?.toString() ?? '');
      this.tourScheduleService.updateSchedule(formData).subscribe({
        next: (data) =>{
          if(data){
            this.updateTourScheduleResponse = data;
            alert("Update Tour Schedule successfully!")
          }
        }
      })
    }
  }

  

  viewDetails(tour: GetTourResponse) {
    if(tour!=undefined && tour.id){
      this.tourDetail = tour;
    this.getTourScheduleByidTour(tour.id);
    }
    this.isDisplayDetails = true;
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
      this.updateDisplayedPages();  // Cập nhật các trang được hiển thị
    }
  }

  filterTours(): void {
    console.log(this.tourSelectedId);
    if (this.tourSelectedId != 0) {
        // Lọc tour theo tourSelectedId
        this.pagedTours = this.getTourScheduleResponse.filter(tour => tour.idTour == this.tourSelectedId);
        this.updatePagedTourFitler(); 
        this.updateDisplayedPages();
    } else {
        // Hiển thị tất cả tour
        this.pagedTours = this.getTourScheduleResponse.slice(); // Tạo một bản sao của danh sách đầy đủ
        this.updatePagedTours();
    }
    console.log(this.pagedTours);

    this.currentPageSchedule = 1; // Đặt lại trang hiện tại về 1 sau khi lọc
    // Cập nhật các tour đã phân trang
}


updatePagedTourFitler(): void {
  const start = (this.currentPageSchedule - 1) * this.pageSize;
  const end = start + this.pageSize;
  this.totalPages = Math.ceil(this.pagedTours.length / this.pageSize);
  this.pages = Array.from({ length: this.totalPages }, (_, i) => i + 1);
} 
  
updatePagedTours(): void {
  const start = (this.currentPageSchedule - 1) * this.pageSize;
  const end = start + this.pageSize;
  this.pagedTours = this.getTourScheduleResponse.slice(start, end); // Cập nhật danh sách tours đã phân trang
  this.totalPages = Math.ceil(this.getTourScheduleResponse.length / this.pageSize);
  this.pages = Array.from({ length: this.totalPages }, (_, i) => i + 1);
} 


  updateDisplayedPages(): void {
    const visiblePagesCount = 5; // Số lượng trang muốn hiển thị cùng lúc
    let startPage = Math.max(1, this.currentPageSchedule - Math.floor(visiblePagesCount / 2));
    let endPage = Math.min(this.totalPages, startPage + visiblePagesCount - 1);
  
    // Điều chỉnh lại nếu startPage đi quá giới hạn
    if (endPage - startPage < visiblePagesCount - 1) {
      startPage = Math.max(1, endPage - visiblePagesCount + 1);
    }
  
    this.pages = Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
  }
  
}
