import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { environment } from '../../environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class VnpayService {

  private baseUrl = `${environment.apiBaseUrl}/api/v1/vnpay`;
  
  constructor(private httpClient: HttpClient, @Inject(PLATFORM_ID) private platformId: Object) { }
}
