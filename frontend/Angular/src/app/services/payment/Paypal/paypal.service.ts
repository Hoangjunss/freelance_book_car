// src/app/services/paypal.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { Inject, PLATFORM_ID } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from '../../environment';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  private baseUrl = `${environment.apiBaseUrl}/api/v1/paypal`;

  constructor(
    private httpClient: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object,
    private router: Router
  ) { }

  payWithPayPal(formData: FormData): Observable<any> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.post(`${this.baseUrl}/pay`, formData, { responseType: 'text', headers }).pipe(
      map((response: string) => {
        window.location.href = response;
      })
    );
  }

  handlePayPalSuccess(paymentId: string, payerId: string,orderId: string): Observable<any> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get(`${this.baseUrl}/pay/success`, {
      params: { paymentId, PayerID: payerId,orderId },
      headers: headers,
      responseType: 'text' as 'json'
    }).pipe(
      map((response: any) => {
        console.log('PayPal success:', response);
        return response;
      })
    );
  }

  handlePayPalCancel() {
    return this.router.navigate(['/payment-cancelled']);
  }

  private createAuthorizationHeader(): HttpHeaders {
    let token = null;

    if (isPlatformBrowser(this.platformId)) {
      token = localStorage.getItem('token');
    }
    if (token) {
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    return new HttpHeaders();
  }
}
