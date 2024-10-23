import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { environment } from '../../environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  private baseUrl = `${environment.apiBaseUrl}/api/v1/paypal`;

  constructor(private httpClient: HttpClient, @Inject(PLATFORM_ID) private platformId: Object,private router: Router) { }

  payWithPayPal(order: any) {
    return this.httpClient.post(`${this.baseUrl}/pay`, order, { responseType: 'text' }).subscribe(
      (response: string) => {
        window.location.href = response;
      },
      (error) => {
        console.error('Payment failed', error);
      }
    );
  }

  handlePayPalSuccess(paymentId: string, payerId: string) {
    return this.httpClient.get(`${this.baseUrl}/pay/success`, {
      params: { paymentId, payerId }
    }).subscribe(
      (response) => {
        console.log('Payment successful:', response);
        this.router.navigate(['/payment-success']);
      },
      (error) => {
        console.error('Payment failed:', error);
        this.router.navigate(['/payment-failed']);
      }
    );
  }
}
