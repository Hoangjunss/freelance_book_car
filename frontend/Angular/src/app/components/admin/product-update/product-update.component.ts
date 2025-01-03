import { Component, OnInit } from '@angular/core';
import { ProductServiceService } from '../../../services/AdminSupplier/product/product-service.service';
import { Product } from '../../../models/AdminSupplier/response/products/product';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-product-update',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-update.component.html',
  styleUrl: './product-update.component.css'
})
export class ProductUpdateComponent implements OnInit {


  constructor(private productService: ProductServiceService, private route: ActivatedRoute, private router: Router){}

  product = new Product();

  ngOnInit(): void {
    this.getProductIdFromUrl();
  }

  getProductIdFromUrl(): void {
    this.route.queryParams.subscribe(params => {
      this.product.id = params['id'];
      if (!this.product.id) {
        alert('ID không tồn tại trong URL!');
        this.router.navigate(['/admin/product']);
      }
      this.getProductById(this.product.id);
    });
  }

  getProductById(id:number){
    this.productService.getProductId(id).subscribe({
      next: (response) => {
        if (response) {
          this.product = response;
        } else {
          alert('Thất bại');
        }
      },
      error: (error) => {
        console.log("Error:", error);
      }
    });
  }

  addImage(): void {
    this.product.listImage.push({ id: null, url: '' });
  }
  

  removeImage(index: number): void {
    this.product.listImage.splice(index, 1);
    console.log(`Ảnh tại vị trí ${index} đã bị xóa.`);
  }
  

  onSingleImageSelected(event: Event, index: number): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      const reader = new FileReader();
  
      reader.onload = (e: any) => {
        this.product.listImage[index].url = e.target.result;
        console.log(`Ảnh tại vị trí ${index} đã cập nhật:`, this.product.listImage[index]);
      };
  
      reader.readAsDataURL(file); // Chuyển file thành URL base64
    }
  }
  

  onUpdate(): void {
    const formData = this.prepareFormData(this.product);

    console.log('FormData Content:');
    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });

    this.productService.updateProduct(formData).subscribe({
      next: (response) => {
        if (response) {
          console.log('Product updated successfully:', response);
          alert('Product updated successfully');
          //this.router.navigate(['/admin/product']);
        }
      },
      error: (error) => {
        console.log('Error:', error);
      }
    })
    // Gửi formData đến server qua service
  }

  // Chuẩn bị FormData từ đối tượng sản phẩm
  private prepareFormData(product: any): FormData {
    const formData = new FormData();

    // Thêm thông tin cơ bản
    formData.append('id', product.id);
    formData.append('name', product.nameProduct);
    formData.append('price', product.price);
    formData.append('description', product.description);
    //formData.append('nameProductType', product.nameProductType);
    //formData.append('address', product.address);
    //formData.append('nameSupplier', product.nameSupplier);
    //formData.append('productStatusActivity', product.productStatusActivity);
    //formData.append('productStatusVerify', product.productStatusVerify);

    // Thêm danh sách hình ảnh
    product.listImage.forEach((image: any, index: number) => {
      formData.append(`images[${index}].id`, image.id);
      formData.append(`images[${index}].url`, image.url);
    });

    // Thêm giá chính thức
    product.officialPriceDTO.forEach((price: any, index: number) => {
      formData.append(`officialPriceDTOS[${index}].id`, price.id);
      formData.append(`officialPriceDTOS[${index}].minQuantity`, price.minQuantity);
      formData.append(`officialPriceDTOS[${index}].maxQuantity`, price.maxQuantity);
      formData.append(`officialPriceDTOS[${index}].price`, price.price);
    });

    return formData;
  }

  // Đóng trang cập nhật và chuyển hướng
  closeFormUpdate(): void {
    this.router.navigate(['/admin/product/productActive']);
  }

}
