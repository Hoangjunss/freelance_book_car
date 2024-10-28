import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { environment } from '../../environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VnpayService {

  private baseUrl = `${environment.apiBaseUrl}/api/v1/vnpay`;
  
  constructor(private httpClient: HttpClient, @Inject(PLATFORM_ID) private platformId: Object,private router: Router) { }

  payWithVNPAY(price: number, id: number) {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get(`${this.baseUrl}/pay`, {
      params: { price: price.toString(), id: id.toString() },
      headers: headers,
      responseType: 'text'
    }).subscribe(
      (response: string) => {
        // Redirect tới URL thanh toán của VNPAY
        window.location.href = response;
      },
      (error) => {
        console.error('Payment failed', error);
      }
    );
  }
  handleVNPAYCallback(queryParams: any): Observable<any> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<boolean>(`${this.baseUrl}/returnPay`, {
      headers: headers,
      params: queryParams,
      responseType: 'text' as 'json'  // Chỉ định kiểu trả về là 'text'
    }).pipe(
      map((response: boolean) => {
        return response;
      })
    );
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
