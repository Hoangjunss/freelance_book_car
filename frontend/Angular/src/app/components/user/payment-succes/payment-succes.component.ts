import { Component, OnInit, Renderer2 } from '@angular/core';
import { VnpayService } from '../../../services/payment/VNPAY/vnpay.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-payment-succes',
  imports: [CommonModule],
  templateUrl: './payment-succes.component.html',
  styleUrls: ['./payment-succes.component.css']
})
export class PaymentSuccesComponent implements OnInit {

  isLoading: boolean = true; // Đang tải dữ liệu

  constructor(
    private vnpayService: VnpayService,
    private route: ActivatedRoute,
    private renderer: Renderer2 // Inject Renderer2
  ) { }

  ngOnInit(): void {
    // Lấy vnp_ResponseCode từ URL
    this.route.queryParams.subscribe(params => {
      const vnp_ResponseCode = params['vnp_ResponseCode'];
      this.handleVNPAYCallback(vnp_ResponseCode);
    });
  }

  handleVNPAYCallback(vnp_ResponseCode: string | null) {
    const icon = document.querySelector('.status-icon');
    const header = document.querySelector('.status-title');
    const paragraph = document.querySelector('.status-message');

    if (vnp_ResponseCode === '00') {
      this.vnpayService.handleVNPAYCallback(vnp_ResponseCode).subscribe({
        next: () => {
          // Sử dụng Renderer2 để thay đổi DOM an toàn hơn
          if (icon && header && paragraph) {
            this.renderer.setProperty(header, 'innerHTML', 'Thanh toán thành công!');
            this.renderer.setProperty(paragraph, 'innerHTML', 'Cảm ơn bạn đã mua hàng. Đơn hàng của bạn đã được xử lý thành công. Chúng tôi sẽ gửi xác nhận đến email của bạn sớm nhất có thể.');
            this.renderer.addClass(icon, 'bi-check-circle-fill'); 
            this.renderer.addClass(icon, 'success-icon');
            this.isLoading = false;
          }
        },
        error: (err) => {
          console.error('Payment failed:', err);
          if (icon && header && paragraph) {
            this.renderer.setProperty(header, 'innerHTML', 'Thanh toán thất bại!');
            this.renderer.setProperty(paragraph, 'innerHTML', 'Vui lòng thử lại sau hoặc liên hệ với bộ phận hỗ trợ của chúng tôi.');
            this.renderer.addClass(icon, 'bi-x-circle-fill');
            this.renderer.addClass(icon, 'fail-icon'); 
            this.isLoading = false;
          }
        }
      });
    } else {
      if (icon && header && paragraph) {
        this.renderer.setProperty(header, 'innerHTML', 'Thanh toán thất bại!');
        this.renderer.setProperty(paragraph, 'innerHTML', 'Vui lòng thử lại sau hoặc liên hệ với bộ phận hỗ trợ của chúng tôi.');
        this.renderer.addClass(icon, 'bi-x-circle-fill'); // Thêm class icon thất bại
        this.renderer.addClass(icon, 'fail-icon'); // Thêm class CSS của icon
        this.isLoading = false;
      }
    }
  }
}
