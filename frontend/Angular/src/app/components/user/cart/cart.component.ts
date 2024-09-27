import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from '../../../services/auth.interceptor';
import { UserService } from '../../../services/user/user.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    UserService
  ],
  imports: [FormsModule,CommonModule,HttpClientModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {
  products = [
    { id: 1, name: 'Phòng khách sạn', price: 100000, quantity: 1, image: 'https://via.placeholder.com/100', type: 'hotel' },
    { id: 2, name: 'Xe hơi', price: 200000, quantity: 2, image: 'https://via.placeholder.com/100', type: 'car' },
    { id: 3, name: 'Vé xem phim', price: 150000, quantity: 1, image: 'https://via.placeholder.com/100', type: 'ticket' }
  ];

  get total() {
    return this.products.reduce((sum, product) => sum + (product.price * product.quantity), 0);
  }

  // Xóa các phương thức tăng/giảm số lượng vì đã chỉnh sửa giao diện
  increaseQuantity(product: any) {
    product.quantity++;
  }

  decreaseQuantity(product: any) {
    if (product.quantity > 0) {
      product.quantity--;
    }
  }
}
