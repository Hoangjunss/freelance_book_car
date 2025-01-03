import { TourService } from '../../../services/product/tour/tour/tour.service';
import { CreateTourRequest } from '../../../models/request/product/tour/tour/create-tour-request';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GetTourResponse } from '../../../models/response/product/tour/tour/get-tour-response';
import { CreateTourResponse } from '../../../models/response/product/tour/tour/create-tour-response';
import { NoDataFoundComponent } from "../no-data-found/no-data-found.component";
import { UpdateTourRequest } from '../../../models/request/product/tour/tour/update-tour-request';
import { UpdateTourResponse } from '../../../models/response/product/tour/tour/update-tour-response';
import { ProductServiceService } from '../../../services/AdminSupplier/product/product-service.service';
import { Product } from '../../../models/AdminSupplier/response/products/product';
import { CreateProductRequest } from '../../../models/AdminSupplier/request/products/create-product-resquest';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tour',
  standalone: true,
  imports: [CommonModule, FormsModule, NoDataFoundComponent],
  templateUrl: './product-pending.component.html',
  styleUrls: ['./product-pending.component.css']
})
export class ProductPendingComponent implements OnInit{
createTourRequest: any = {
  name: '',
  price: 0,
  description: '',
  officialPriceDTOS: [],
};
selectProduct: any;
  createTourResponse: CreateTourResponse = new CreateTourResponse();
  updateTourRequest: Product = new Product();
  updateTourResponse: UpdateTourResponse = new UpdateTourResponse();

  selectedImages: string[] = [];

  imageUrl: string = 'assets/img/DEFAULT/tour-default.png';
  getALlTour: Product[] = [];
  filterTour: Product[] = [];

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

  totalPages = 0; // Tổng số trang

  searchQuery: string='';

  constructor(private tourService:ProductServiceService, private router: Router){}

  ngOnInit(): void {
    this.getAllTour(this.currentPage);
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

  displayFromDetail(id: number): void{
    this.selectProduct = this.getProductDetail(id);
    
  }

  getProductDetail(id: number){
    this.tourService.getProductId(id).subscribe({
      next: (data)=>{
        if(data){
          this.selectProduct = data;
        }
      }
    })
  }
  

  get pages(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }

  displayFormCreate(){
    this.router.navigate(['/admin/product/create']);
  }

  onImagesSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && this.selectedImages.length < 4) {
      for (let i = 0; i < input.files.length; i++) {
        if (this.selectedImages.length >= 4) break;
        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.selectedImages.push(e.target.result);
        };
        reader.readAsDataURL(input.files[i]);
      }
    }
  }
  removeImage(index: number): void {
    this.selectedImages.splice(index, 1);
  }

  addOfficialPrice(): void {
    if (this.createTourRequest.officialPriceDTOS.length < 4) {
      this.createTourRequest.officialPriceDTOS.push({ price: 0, minQuantity: 0, maxQuantity: 0 });
    }
  }
  
  removeOfficialPrice(index: number): void {
    this.createTourRequest.officialPriceDTOS.splice(index, 1);
  }
  closeFormCreate(){
    this.isDisplayCreate = false;
  }

  displayFromUpdate(tour: Product){
    this.router.navigate(['/admin/product/update'], {
      queryParams: { id: tour.id }
    });
  }

  closeFormUpdate(){
    this.imageUri = 'assets/img/DEFAULT/tour-default.png';
    this.isDisplayUpdate = false;
  }

  onImageSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.imageFile = file;
      const reader = new FileReader();
      reader.onload = () => {
        this.imageUri = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
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
    /* console.log('Search Query:', this.searchQuery);
    if (this.searchQuery.trim() != '') {
      this.filterTour = this.getALlTour.filter(hotel =>
        hotel.name?.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
  
      console.log(this.filterTour);
      
      this.currentPage = 1; */
      
      this.updatePagedData();
    
  }

/*   reset(){
    this.filterTour = this.getALlTour;
    this.updatePagedData();
  } */



  //On Submit
  onCreate(): void {
    const formData = new FormData();
    formData.append('name', this.createTourRequest.name);
    formData.append('price', this.createTourRequest.price.toString());
    formData.append('description', this.createTourRequest.description);
  
    this.selectedImages.forEach((image, index) => {
      formData.append(`images[${index}]`, image);
    });
  
    this.createTourRequest.officialPriceDTOS.forEach((price: { price: number; minQuantity: number; maxQuantity: number }, index: number) => {
      formData.append(`officialPriceDTOS[${index}][price]`, price.price.toString());
      formData.append(`officialPriceDTOS[${index}][minQuantity]`, price.minQuantity.toString());
      formData.append(`officialPriceDTOS[${index}][maxQuantity]`, price.maxQuantity.toString());
    });
    console.log('FormData:',formData);
    this.closeFormCreate();
  }
  

  onUpdate(){
/*     console.log(this.updateTourRequest);
    if(!this.updateTourRequest?.id){
      alert('Not Found Tour Update');
      return;
    }
    if (!this.updateTourRequest?.name || !this.updateTourRequest?.startLocation || !this.updateTourRequest?.description) {
      alert('Please fill in all required fields: Name, Location, Description');
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
    formData.append('isActive', this.updateTourRequest.isActive ? 'true' : 'false');
  
    if (this.imageFile != undefined) {
      formData.append('image', this.imageFile);
    }

    console.log(this.updateTourRequest);
    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });

    this.tourService.updateTour(formData).subscribe({
      next: (data) => {
        this.updateTourRequest = data;
        if (this.updateTourRequest) {
          console.log('Tour created successfully:', this.updateTourRequest);
          alert('Tour created successfully');
          window.location.reload();
        }
      },
      error: (err) => {
        console.error('Error creating tour:', err.message);
      }
    });     */
  }

  //Get all Tour
  getAllTour(page:number){
    this.tourService.getAllProductAccess(page).subscribe({
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
  nextPage() { 
    this.currentPage++;
     this.getAllTour(this.currentPage);
     }
      prevPage() {
       if (this.currentPage > 0) { 
        this.currentPage--;
         this.getAllTour(this.currentPage); 
        } 
      }





}
