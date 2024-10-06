import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GetTourismResponse } from '../../../../models/response/product/ticket/tourism/get-tourism-response';
import { CreateTourismRequest } from '../../../../models/request/product/ticket/tourism/create-tourism-request';
import { CreateTourismResponse } from '../../../../models/response/product/ticket/tourism/create-tourism-response';
import { TourismService } from '../../../../services/product/ticket/tourism/tourism.service';
import { NoDataFoundComponent } from "../../no-data-found/no-data-found.component";
import { UpdateTourismRequest } from '../../../../models/request/product/ticket/tourism/update-tourism-request';
import { NotificationComponent } from '../../../notification/notification.component';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-tourism',
  standalone: true,
  imports: [CommonModule, FormsModule, NoDataFoundComponent,NotificationComponent],
  templateUrl: './tourism.component.html',
  styleUrls: ['./tourism.component.css']
})
export class TourismComponent {
  createTourismRequest: CreateTourismRequest = new CreateTourismRequest();
  createTourismResponse: CreateTourismResponse = new CreateTourismResponse();
  updateTourismRequest: UpdateTourismRequest = new UpdateTourismRequest();
  updateTourismResponse: CreateTourismResponse = new CreateTourismResponse();
  getTourismResponse: GetTourismResponse[] = [];
  filterTourism: GetTourismResponse[] = [];

  isDisplayCreate = false;
  isDisplayUpdate = false;

  imageUrl: string = 'assets/img/DEFAULT/tour-default.png';
  selectedImage: string = 'assets/img/DEFAULT/tour-default.png';

  imageFile?: File;
  imageUri?: string;
  searchQuery: string='';

  tour?: GetTourismResponse;
  currentPage: number = 1;
  pageSize: number = 5;
  pagedData: any[] = [];

  @ViewChild(NotificationComponent) notificationComponent!: NotificationComponent;

  constructor(private tourismService: TourismService, private title:Title){
    this.title.setTitle('Danh sách khu du dịch')
  }

  ngOnInit(): void {
    this.getAllTourisms();
    this.updatePagedData();
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.updatePagedData();
  }

  updatePagedData() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.pagedData = this.filterTourism.slice(startIndex, endIndex);
  }

  get totalPages(): number {
    return Math.ceil(this.filterTourism.length / this.pageSize);
  }

  get pages(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }

  displayFormCreate(){
    this.isDisplayCreate = true;
  }

  closeFormCreate(){
    this.isDisplayCreate = false;
  }

  searchTour() {
    console.log('Search Query:', this.searchQuery);
    if (this.searchQuery.trim() != '') {
      this.filterTourism = this.getTourismResponse.filter(hotel =>
        hotel.name?.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
  
      console.log(this.filterTourism);
      
      this.currentPage = 1;
      
      this.updatePagedData();
    }
  }

  reset(){
    this.filterTourism = this.getTourismResponse;
    this.updatePagedData();
  }

  displayFormUpdate(tourism: GetTourismResponse){
    this.updateTourismRequest = {
      id: tourism.id,
      name: tourism.name,
      location: tourism.location,
      description: tourism.description,
      rating: tourism.rating,
    };
    this.imageUri = tourism.image;
    this.isDisplayUpdate = true;
  }

  closeFormUpdate(){
    this.imageUri='assets/img/DEFAULT/tour-default.png';
    this.isDisplayUpdate = false;
  }

  onImageSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.imageFile = file; // Gán tệp ảnh đã chọn vào thuộc tính image của createTourRequest
      const reader = new FileReader();
      reader.onload = () => {
        this.imageUri = reader.result as string; // Hiển thị ảnh vừa chọn trong form
      };
      reader.readAsDataURL(file);
    }
  }

  onCreate() {
  
    if (!this.createTourismRequest?.name || !this.createTourismRequest?.location || !this.createTourismRequest?.description) {
      this.notificationComponent.showNotification('error', 'Vui lòng điền đầy đủ thông tin');
      return;
    }

    const formData = new FormData();
    formData.append('name', this.createTourismRequest.name || '');
    formData.append('description', this.createTourismRequest.description || '');
    formData.append('location', this.createTourismRequest.location || '');
    formData.append('rating', this.createTourismRequest.rating?.toString() || '');
  
    // Kiểm tra và thêm hình ảnh vào FormData nếu có
    if (this.imageFile != undefined) {
      formData.append('image', this.imageFile);
    }else{
      this.notificationComponent.showNotification('error', 'Vui lòng chọn ảnh');
      return;
    }


    // Gọi service để tạo tour
    this.tourismService.createTour(formData).subscribe({
      next: (data) => {
        this.createTourismResponse = data;
        if (this.createTourismResponse) {
          this.notificationComponent.showNotification('success', 'Tạo khu du lịch thành công');
          window.location.reload();
        }
      },
      error: (err) => {
        console.error('Error creating tour:', err.message);
      }
    });
  }

  onUpdate(){
    if(!this.updateTourismRequest?.id){
      this.notificationComponent.showNotification('error', 'Tourism Update Not Found');
      return;
    }
    // Kiểm tra các trường bắt buộc
    if (!this.updateTourismRequest?.name || !this.updateTourismRequest?.location || !this.updateTourismRequest?.description) {
      this.notificationComponent.showNotification('error', 'Vui lòng điền đầy đủ thông tin');
      return;
    }

    const formData = new FormData();
    formData.append('id', this.updateTourismRequest.id.toString() || '')
    formData.append('name', this.updateTourismRequest.name || '');
    formData.append('description', this.updateTourismRequest.description || '');
    formData.append('location', this.updateTourismRequest.location || '');
    formData.append('rating', this.updateTourismRequest.rating?.toString() || '');
  
    if (this.imageFile != undefined) {
      formData.append('image', this.imageFile);
    }

    this.tourismService.updateTour(formData).subscribe({
      next: (data) => {
        this.createTourismResponse = data;
        if (this.createTourismResponse) {
          this.notificationComponent.showNotification('success', 'Cập nhật khu du lịch thành công');
          window.location.reload();
        }
      },
      error: (err) => {
        console.error('Error creating tour:', err.message);
      }
    });
  }

  getAllTourisms(){
    this.tourismService.getAllTourism().subscribe({
      next: (data) =>{
        this.getTourismResponse = data;
        this.filterTourism = this.getTourismResponse;
        this.updatePagedData();
      },
      error: (err) => {
        console.error('Error get all tourism:', err.message);
      }
    })
  }

}
