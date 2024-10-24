import { Component, OnInit, Inject, PLATFORM_ID, Renderer2 } from '@angular/core';
import { VnpayService } from '../../../services/payment/VNPAY/vnpay.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { PaypalService } from '../../../services/payment/Paypal/paypal.service';
import { Title } from '@angular/platform-browser';

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
    private renderer: Renderer2,
    private title: Title,
    private paypalService: PaypalService,
    @Inject(PLATFORM_ID) private platformId: Object // Inject PLATFORM_ID
  ) { this.title.setTitle('Thanh toán thành công'); }

  ngOnInit(): void {
    // Lấy vnp_ResponseCode từ URL
    this.route.queryParams.subscribe(params => {
      const vnp_ResponseCode = params['vnp_ResponseCode'];
      console.log(vnp_ResponseCode);
      this.handleVNPAYCallback(vnp_ResponseCode);
      
    });
  }

  handleVNPAYCallback(vnp_ResponseCode: string | null) {
    if (isPlatformBrowser(this.platformId)) {
      const icon = document.querySelector('.status-icon');
      const header = document.querySelector('.status-title');
      const paragraph = document.querySelector('.status-message');
  
      if (vnp_ResponseCode === '00') {
        console.log('Starting VNPAY callback...');
        this.vnpayService.handleVNPAYCallback(vnp_ResponseCode).subscribe({
          next: (response: any) => {
            const isSuccess = (response === true || response === 'true');
            console.log('Payment response:', response, 'Type:', typeof response, 'isSuccess:', isSuccess);
  
            if (isSuccess) {
              console.log('Payment success!');
              if (icon && header && paragraph) {
                this.renderer.setProperty(header, 'innerHTML', 'Thanh toán thành công!');
                this.renderer.setProperty(paragraph, 'innerHTML', 'Cảm ơn bạn đã mua hàng. Đơn hàng của bạn đã được xử lý thành công. Chúng tôi sẽ gửi xác nhận đến email của bạn sớm nhất có thể.');
                this.renderer.addClass(icon, 'bi-check-circle-fill');
                this.renderer.addClass(icon, 'success-icon');
                this.isLoading = false;
              }
            } else {
              console.log('Payment failed!');
              // Nếu phản hồi là false (thanh toán thất bại)
              if (icon && header && paragraph) {
                this.renderer.setProperty(header, 'innerHTML', 'Thanh toán thất bại!');
                this.renderer.setProperty(paragraph, 'innerHTML', 'Vui lòng thử lại sau hoặc liên hệ với bộ phận hỗ trợ của chúng tôi.');
                this.renderer.addClass(icon, 'bi-x-circle-fill');
                this.renderer.addClass(icon, 'fail-icon');
                this.isLoading = false;
              }
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
          this.renderer.addClass(icon, 'bi-x-circle-fill');
          this.renderer.addClass(icon, 'fail-icon');
          this.isLoading = false;
        }
      }
    }
  }

  handlePayPalCallback() {
    if (isPlatformBrowser(this.platformId)) {
      const icon = document.querySelector('.status-icon');
      const header = document.querySelector('.status-title');
      const paragraph = document.querySelector('.status-message');

      console.log('Starting PayPal callback...');
      this.paypalService.handlePayPalSuccess('paymentId', 'payerId');
    }
  }
  
  
  
}
