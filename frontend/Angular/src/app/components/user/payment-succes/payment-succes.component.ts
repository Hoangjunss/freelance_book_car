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
  ) { this.title.setTitle('Kết quả thanh toán'); }

  ngOnInit(): void {
    // Lấy các tham số từ URL
    this.route.queryParams.subscribe(params => {
      const vnp_ResponseCode = params['vnp_ResponseCode'];
      const paymentId = params['paymentId'];
      const payerId = params['PayerID'];
      const contractId = params['contractId'];
      const orderId = params['orderId'];
      
      console.log('vnp_ResponseCode:', vnp_ResponseCode, 'paymentId:', paymentId, 'payerId:', payerId);
      if (vnp_ResponseCode && contractId) {
        // Xử lý callback của VNPAY
        this.handleVNPAYCallback(vnp_ResponseCode, contractId);
      } else if (paymentId && payerId && orderId) {
        // Xử lý callback của PayPal
        this.handlePayPalCallback(paymentId, payerId, orderId);
      } else {
        // Nếu không có tham số phù hợp
        this.handlePaymentFailure('Không tìm thấy thông tin thanh toán.');
      }
    });
  }

  handleVNPAYCallback(vnp_ResponseCode: string, contractId: string) {
    if (isPlatformBrowser(this.platformId)) {
      const icon = document.querySelector('.status-icon');
      const header = document.querySelector('.status-title');
      const paragraph = document.querySelector('.status-message');
  
      if (vnp_ResponseCode === '00' && contractId) {
        console.log('Starting VNPAY callback...');
        const queryParams = { vnp_ResponseCode, contractId }; 
        this.vnpayService.handleVNPAYCallback(queryParams).subscribe({
          next: (response: any) => {
            const parsedResponse = JSON.parse(response);
            const isSuccess = parsedResponse.success === true;
            console.log('Payment response:', response, 'Type:', typeof response, 'isSuccess:', isSuccess);
  
            if (isSuccess) {
              this.handlePaymentSuccess(icon, header, paragraph);
            } else {
              this.handlePaymentFailure('Thanh toán thất bại. Vui lòng thử lại sau.');
            }
          },
          error: (err) => {
            console.error('Payment failed:', err);
            this.handlePaymentFailure('Thanh toán thất bại. Vui lòng thử lại sau.');
          }
        });
      } else {
        this.handlePaymentFailure('Thanh toán thất bại. Vui lòng thử lại sau.');
      }
    }
  }

  handlePayPalCallback(paymentId: string, payerId: string, orderId: string) {
    if (isPlatformBrowser(this.platformId)) {
      const icon = document.querySelector('.status-icon');
      const header = document.querySelector('.status-title');
      const paragraph = document.querySelector('.status-message');

      console.log('Starting PayPal callback...');
        this.paypalService.handlePayPalSuccess(paymentId, payerId,orderId).subscribe({
          next: (response: any) => {
            console.log('PayPal success:', response);
            this.handlePaymentSuccess(icon, header, paragraph);
          },
          error: (err) => {
            console.error('Payment failed:', err);
            this.handlePaymentFailure('Thanh toán thất bại. Vui lòng thử lại sau.');
          }
        }
      );
    }
  }

  handlePaymentSuccess(icon: Element | null, header: Element | null, paragraph: Element | null) {
    if (icon && header && paragraph) {
      this.renderer.setProperty(header, 'innerHTML', 'Thanh toán thành công!');
      this.renderer.setProperty(paragraph, 'innerHTML', 'Cảm ơn bạn đã mua hàng. Đơn hàng của bạn đã được xử lý thành công. Chúng tôi sẽ gửi xác nhận đến email của bạn sớm nhất có thể.');
      this.renderer.addClass(icon, 'bi-check-circle-fill');
      this.renderer.addClass(icon, 'success-icon');
      this.isLoading = false;
    }
  }

  handlePaymentFailure(message: string) {
    const icon = document.querySelector('.status-icon');
    const header = document.querySelector('.status-title');
    const paragraph = document.querySelector('.status-message');

    if (icon && header && paragraph) {
      this.renderer.setProperty(header, 'innerHTML', 'Thanh toán thất bại!');
      this.renderer.setProperty(paragraph, 'innerHTML', message);
      this.renderer.addClass(icon, 'bi-x-circle-fill');
      this.renderer.addClass(icon, 'fail-icon');
      this.isLoading = false;
    }
  }
}
