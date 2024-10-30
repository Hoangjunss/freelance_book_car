import { TourService } from './../../../../services/product/tour/tour/tour.service';
import { CreateTourRequest } from './../../../../models/request/product/tour/tour/create-tour-request';
import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GetTourResponse } from '../../../../models/response/product/tour/tour/get-tour-response';
import { CreateTourResponse } from '../../../../models/response/product/tour/tour/create-tour-response';
import { NoDataFoundComponent } from "../../no-data-found/no-data-found.component";
import { UpdateTourRequest } from '../../../../models/request/product/tour/tour/update-tour-request';
import { UpdateTourResponse } from '../../../../models/response/product/tour/tour/update-tour-response';
import { NotificationComponent } from '../../../notification/notification.component';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-tour',
  standalone: true,
  imports: [CommonModule, FormsModule, NoDataFoundComponent,NotificationComponent],
  templateUrl: './tour.component.html',
  styleUrl: './tour.component.css'
})
export class TourComponent implements OnInit{
  createTourRequest: CreateTourRequest = new CreateTourRequest();
  createTourResponse: CreateTourResponse = new CreateTourResponse();
  updateTourRequest: UpdateTourRequest = new UpdateTourRequest();
  updateTourResponse: UpdateTourResponse = new UpdateTourResponse();

  imageUriFirst?: string = 'assets/img/DEFAULT/tour-default.png';
  imageUriSecond?: string = 'assets/img/DEFAULT/tour-default.png';
  imageUriThird?: string = 'assets/img/DEFAULT/tour-default.png';
  imageUriMap?: string = 'assets/img/DEFAULT/tour-default.png';

  imageUrl: string = 'assets/img/DEFAULT/tour-default.png';
  getALlTour: GetTourResponse[] = [];
  filterTour: GetTourResponse[] = [];

  selectedImage: string = 'assets/img/DEFAULT/tour-default.png';
  isDisplayUpdate: boolean = false;
  isDisplayCreate: boolean = false;
  tour?: GetTourResponse;
  currentPage: number = 1;
  pageSize: number = 5;
  pagedData: any[] = [];
  imageId?: string;
  imageFile!: File;
  imageUri?: string = 'assets/img/DEFAULT/tour-default.png';
  isLoading: boolean = false;

  // Các tệp ảnh được chọn
  imageFirstFile!: File;
  imageSecondFile!: File;
  imageThirdFile!: File;
  imageMapFile!: File;


  searchQuery: string='';

  @ViewChild(NotificationComponent) notificationComponent!: NotificationComponent;

  constructor(private tourService:TourService, private title:Title){
    this.title.setTitle('Quản lý chuyến đi')
  }

  ngOnInit(): void {
    this.getAllTour();
    this.updatePagedData();
    console.log(this.selectedImage);
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.updatePagedData();
  }

  updatePagedData() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.pagedData = this.filterTour.slice(startIndex, endIndex);
  }
  

  get totalPages(): number {
    return Math.ceil(this.filterTour.length / this.pageSize);
  }

  get pages(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }

  displayFormCreate(){
    this.isDisplayCreate = true;
  }

  // Phương thức đóng form tạo tour
  closeFormCreate() {
    this.isDisplayCreate = false;
    // Reset form và ảnh
    this.createTourRequest = new CreateTourRequest();
    this.imageUriFirst = 'assets/img/DEFAULT/tour-default.png';
    this.imageUriSecond = 'assets/img/DEFAULT/tour-default.png';
    this.imageUriThird = 'assets/img/DEFAULT/tour-default.png';
    this.imageUriMap = 'assets/img/DEFAULT/tour-default.png';
  }

  displayFormUpdate(tour: GetTourResponse){
    this.updateTourRequest = {
      id: tour.id,
      name: tour.name || '',
      description: tour.description || '',
      startLocation: tour.startLocation || '',
      endLocation: tour.endLocation || '',
      isActive: tour.isActive || false,
      // Các trường ảnh sẽ được xử lý thông qua FormData
    };
    this.imageUriFirst = tour.imageFirst ? tour.imageFirst : 'assets/img/DEFAULT/tour-default.png';
    this.imageUriSecond = tour.imageSecond ? tour.imageSecond : 'assets/img/DEFAULT/tour-default.png';
    this.imageUriThird = tour.imageThird ? tour.imageThird : 'assets/img/DEFAULT/tour-default.png';
    this.imageUriMap = tour.imageMap ? tour.imageMap : 'assets/img/DEFAULT/tour-default.png';
    this.isDisplayUpdate = true;
  }

  closeFormUpdate() {
    this.isDisplayUpdate = false;
    // Reset form và ảnh
    this.updateTourRequest = new UpdateTourRequest();
    this.imageUriFirst = 'assets/img/DEFAULT/tour-default.png';
    this.imageUriSecond = 'assets/img/DEFAULT/tour-default.png';
    this.imageUriThird = 'assets/img/DEFAULT/tour-default.png';
    this.imageUriMap = 'assets/img/DEFAULT/tour-default.png';
  }

  createFileFromUrl(url: string, fileName: string): Promise<File> {
    return fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.blob(); // Chuyển đổi phản hồi thành blob
        })
        .then(blob => {
            return new File([blob], fileName, { type: blob.type }); // Tạo đối tượng File từ blob
        });
  }

  searchTour() {
    console.log('Search Query:', this.searchQuery);
    if (this.searchQuery.trim() != '') {
      this.filterTour = this.getALlTour.filter(hotel =>
        hotel.name?.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
  
      console.log(this.filterTour);
      
      this.currentPage = 1;
      
      this.updatePagedData();
    }
  }

  reset(){
    this.filterTour = this.getALlTour;
    this.updatePagedData();
  }



  // On Submit
  onCreate() {
    this.isLoading = true;
    console.log(this.createTourRequest);
    if (!this.createTourRequest?.name || 
        !this.createTourRequest?.startLocation || 
        !this.createTourRequest?.description) {
      this.notificationComponent.showNotification('error', 'Vui lòng điền đầy đủ thông tin');
      return;
    }

    if(this.createTourRequest.isActive == undefined ){
      this.createTourRequest.isActive = false;
    }

    const formData = new FormData();
    formData.append('name', this.createTourRequest.name || '');
    formData.append('description', this.createTourRequest.description || '');
    formData.append('startLocation', this.createTourRequest.startLocation || '');
    formData.append('endLocation', this.createTourRequest.endLocation || '');
    formData.append('isActive', this.createTourRequest.isActive ? 'true' : 'false');

    // Kiểm tra và thêm từng ảnh vào FormData
    if (this.imageFirstFile) {
      formData.append('imageFirst', this.imageFirstFile);
    } else {
      this.notificationComponent.showNotification('error', 'Vui lòng chọn ảnh đầu tiên');
      return;
    }

    if (this.imageSecondFile) {
      formData.append('imageSecond', this.imageSecondFile);
    } else {
      this.notificationComponent.showNotification('error', 'Vui lòng chọn ảnh thứ hai');
      return;
    }

    if (this.imageThirdFile) {
      formData.append('imageThird', this.imageThirdFile);
    } else {
      this.notificationComponent.showNotification('error', 'Vui lòng chọn ảnh thứ ba');
      return;
    }

    if (this.imageMapFile) {
      formData.append('imageMap', this.imageMapFile);
    } else {
      this.notificationComponent.showNotification('error', 'Vui lòng chọn ảnh bản đồ');
      return;
    }

    console.log('FormData contents:');
    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });

    this.tourService.createTour(formData).subscribe({
      next: (data) => {
        this.createTourResponse = data;
        if (this.createTourResponse) {
          this.isLoading = false;
          console.log('Tour created successfully:', this.createTourResponse);
          this.notificationComponent.showNotification('success', 'Tạo tour thành công');
          window.location.reload();
        }
      },
      error: (err) => {
        console.error('Error creating tour:', err.message);
        this.notificationComponent.showNotification('error', 'Có lỗi xảy ra khi tạo tour');
        this.isLoading = false;
      }
    });
  }



  onUpdate() {
    this.isLoading = true;
    console.log(this.updateTourRequest);
    if(!this.updateTourRequest?.id){
      this.notificationComponent.showNotification('error', 'Không tìm thấy tour cần cập nhật');
      return;
    }
    if (!this.updateTourRequest?.name || !this.updateTourRequest?.startLocation || !this.updateTourRequest?.description) {
      this.notificationComponent.showNotification('error', 'Vui lòng điền đầy đủ thông tin');
      return;
    }

    if(this.updateTourRequest.isActive == undefined ){
      this.updateTourRequest.isActive = false;
    }
  
    const formData = new FormData();
    formData.append('id', this.updateTourRequest.id.toString() || '');
    formData.append('name', this.updateTourRequest.name || '');
    formData.append('description', this.updateTourRequest.description || '');
    formData.append('startLocation', this.updateTourRequest.startLocation || '');
    formData.append('endLocation', this.updateTourRequest.endLocation || '');
    formData.append('isActive', this.updateTourRequest.isActive ? 'true' : 'false');

    // Kiểm tra và thêm từng ảnh vào FormData nếu có thay đổi
    if (this.imageFirstFile) {
      formData.append('imageFirst', this.imageFirstFile);
    }

    if (this.imageSecondFile) {
      formData.append('imageSecond', this.imageSecondFile);
    }

    if (this.imageThirdFile) {
      formData.append('imageThird', this.imageThirdFile);
    }

    if (this.imageMapFile) {
      formData.append('imageMap', this.imageMapFile);
    }

    console.log('FormData contents:');
    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });

    this.tourService.updateTour(formData).subscribe({
      next: (data: UpdateTourResponse) => {
        // Không nên gán data vào updateTourRequest vì chúng khác loại
        console.log('Tour updated successfully:', data);
        this.notificationComponent.showNotification('success', 'Cập nhật tour thành công');
        this.isLoading = false;
        window.location.reload();
      },
      error: (err) => {
        console.error('Error updating tour:', err.message);
        this.notificationComponent.showNotification('error', 'Có lỗi xảy ra khi cập nhật tour');
      }
    });    
  }

  //Get all Tour
  getAllTour(){
    this.tourService.getAllTour().subscribe({
      next: (data) => {
        this.getALlTour = data;
        this.filterTour = this.getALlTour;
        this.updatePagedData();
      },
      error: (err) => {
        console.error('Error getting tours:', err.message);
      }
    })
  }

  /**
   * Xử lý khi người dùng chọn ảnh
   * @param event Sự kiện chọn ảnh
   * @param imageType Loại ảnh đang được chọn ('imageFirst', 'imageSecond', 'imageThird', 'imageMap')
   */
  onImageSelected(event: any, imageType: string) {
    const file: File = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        switch(imageType) {
          case 'imageFirst':
            this.imageUriFirst = reader.result as string;
            break;
          case 'imageSecond':
            this.imageUriSecond = reader.result as string;
            break;
          case 'imageThird':
            this.imageUriThird = reader.result as string;
            break;
          case 'imageMap':
            this.imageUriMap = reader.result as string;
            break;
        }
      };
      reader.readAsDataURL(file);

      // Lưu trữ tệp ảnh vào biến tương ứng
      switch(imageType) {
        case 'imageFirst':
          this.imageFirstFile = file;
          break;
        case 'imageSecond':
          this.imageSecondFile = file;
          break;
        case 'imageThird':
          this.imageThirdFile = file;
          break;
        case 'imageMap':
          this.imageMapFile = file;
          break;
      }
    }
  }



}
