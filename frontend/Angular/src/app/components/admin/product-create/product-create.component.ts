import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NoDataFoundComponent } from '../no-data-found/no-data-found.component';
import { ProductServiceService } from '../../../services/AdminSupplier/product/product-service.service';
import { Product } from '../../../models/AdminSupplier/response/products/product';
import { CreateProductRequest } from '../../../models/AdminSupplier/request/products/create-product-resquest';
import { Router } from '@angular/router';
import { ProductGroup } from '../../../models/AdminSupplier/response/danhmucsanpham/product-group';

@Component({
  selector: 'app-product-create',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-create.component.html',
  styleUrl: './product-create.component.css'
})
export class ProductCreateComponent implements OnInit {



  constructor(private productService: ProductServiceService, private router: Router){}

  createProductRequest = {
    name: '',
    price: 0,
    description: '',
    productTypeId: 0,
    supplierId: 1,
    images: [] as File[],
    officialPriceDTOS: [] as {
      price: number;
      minQuantity: number;
      maxQuantity: number;
    }[]
  };
  ngOnInit(): void {
    this.getProductGroup();
   ;
   
  }
  getProductGroup(){
    this.productService.GetProductGroup().subscribe({
      next: (data) => {
        this.productGroup = data;
      
      },
      error: (err) => {
        console.error('Error getting tours:', err.message);
      }
    })
  }
  getProductType(productGroupId:number){
    this.productService.GetProductType(productGroupId).subscribe({
      next: (data) => {
        this.productTypes = data;
      
      },
      error: (err) => {
        console.error('Error getting tours:', err.message);
      }
    })
  }
  onProductGroupChange(event: any) { 
    const selectedId = event.target.value; 
    this.selectedProductGroupId = selectedId; 
    this.getProductType(selectedId); 
    console.log('productType')
  }
  imageUrls: string[] = [];
  onImageSelected1(event: any, index: number) { 
    const file = event.target.files[0]; 
    if (file) { const reader = new FileReader(); reader.onload = (e: any) => {
       this.imageUrls[index] = e.target.result;
       }; reader.readAsDataURL(file);
       } 
      }

  officialPrices = Array.from({ length: 4 }, () => ({
    price: 0,
    minQuantity: 0,
    maxQuantity: 0
  }));
  

  selectedImages: (string | ArrayBuffer | null)[] = [];

  product: Product = new Product();
 isProductGroup=false;
  productTypes : any[]=[];
  selectedProductGroupId: number | null =null;


  productGroup: any[] = [];
  onImagesSelected(event: any): void {
    const files: FileList = event.target.files;
    this.selectedImages = [];
    this.createProductRequest.images = [];

    Array.from(files).forEach((file: File) => {
      if (this.createProductRequest.images.length < 4) {
        this.createProductRequest.images.push(file);
        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.selectedImages.push(e.target.result);
        };
        reader.readAsDataURL(file);
      }
    });
  }

  onImageSelected(event: Event, index: number) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files[0]) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.selectedImages[index] = e.target?.result;
      };
      reader.readAsDataURL(fileInput.files[0]);
      this.createProductRequest.images[index] = fileInput.files[0];
    }
  }

  removeImage(index: number): void {
    this.selectedImages.splice(index, 1);
    this.createProductRequest.images.splice(index, 1);
  }

  closeFormCreate(): void {
    this.router.navigate(['/admin/product/productAccess']);
  }


  onCreate() {
    this.createProductRequest.officialPriceDTOS = this.officialPrices.map(priceObj => ({
      price: priceObj.price,
      minQuantity: priceObj.minQuantity,
      maxQuantity: priceObj.maxQuantity
    }));

    const formData = new FormData();

    // Thêm thông tin cơ bản
    formData.append('nameProduct', this.createProductRequest.name);
    formData.append('price', this.createProductRequest.price.toString());
    formData.append('description', this.createProductRequest.description);
    formData.append('productTypeId', this.createProductRequest.productTypeId.toString());
  
    // Thêm ảnh (File[])
    this.createProductRequest.images.forEach((image, index) => {
      formData.append(`images[${index}]`, image);
    });
  
    // Thêm giá chính thức (officialPriceDTOS)
    this.officialPrices.forEach((priceDetail, index) => {
      formData.append(`officialPriceDTOS[${index}].price`, priceDetail.price.toString());
      formData.append(`officialPriceDTOS[${index}].minQuantity`, priceDetail.minQuantity.toString());
      formData.append(`officialPriceDTOS[${index}].maxQuantity`, priceDetail.maxQuantity.toString());
    });

      // Log dữ liệu trong FormData
  console.log('FormData Content:');
  formData.forEach((value, key) => {
    console.log(`${key}: ${value}`);
  });
    
  
  this.productService.createProduct(formData).subscribe({
    next: (response) => {
      if (response) {
        console.log('Product created successfully:', response);
        alert('Product created successfully');
        this.closeFormCreate();
      }
    },
    error: (error) => {
      console.log('Create product error:', error);
    }
  })
  }


}
